package com.galvis.tvhome;

import android.graphics.Rect;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.FrameLayout;

/**
 * In-launcher idle clock screensaver that morphs directly from Home's clock into the
 * full-screen ClockScene (ease-out) and shrinks back to Home's clock on wake (ease-out).
 * Reuses a single inflated overlay with GPU ViewPropertyAnimator transforms so no extra
 * Activity or Window is allocated on low-RAM TVs.
 */
final class IdleClockOverlay {

    private static final long ENTER_MS = 550L;
    private static final long EXIT_MS = 450L;
    private static final Interpolator EASE_OUT = new DecelerateInterpolator(2.2f);

    private final FrameLayout root;
    private View overlay;
    private View backdrop;
    private View ambient;
    private View content;
    private View dreamTime;
    private ClockScene scene;

    private View activeHomeClock;
    private View activeHomeClockBlock;
    private boolean showing;
    private boolean dismissing;

    IdleClockOverlay(FrameLayout root) {
        this.root = root;
    }

    boolean isActive() {
        return showing;
    }

    void show(View homeClock, View homeClockBlock) {
        if (showing || homeClock == null || homeClockBlock == null) return;
        ensureInflated();
        showing = true;
        dismissing = false;
        activeHomeClock = homeClock;
        activeHomeClockBlock = homeClockBlock;

        cancelAnimations();
        scene.stop();

        overlay.setVisibility(View.VISIBLE);
        overlay.bringToFront();
        backdrop.setAlpha(0f);
        ambient.setAlpha(0f);
        content.setAlpha(0f);

        overlay.getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                overlay.getViewTreeObserver().removeOnPreDrawListener(this);
                if (!showing || dismissing) return true;
                Rect homeRect = homeClockRect(homeClock);
                float startScale = scaleToMatchHome(homeClock);

                content.setPivotX(0f);
                content.setPivotY(0f);
                content.setX(homeRect.left);
                content.setY(homeRect.top);
                content.setScaleX(startScale);
                content.setScaleY(startScale);
                content.setAlpha(1f);

                homeClockBlock.animate().setStartDelay(0L).alpha(0f).setDuration(150L).start();

                float targetX = Math.max(0f, (root.getWidth() - content.getWidth()) * 0.5f);
                float targetY = Math.max(0f, (root.getHeight() - content.getHeight()) * 0.5f);

                backdrop.animate().alpha(1f).setDuration(ENTER_MS).setInterpolator(EASE_OUT).start();
                ambient.animate().alpha(1f).setDuration(ENTER_MS).setInterpolator(EASE_OUT).start();
                content.animate()
                        .x(targetX)
                        .y(targetY)
                        .scaleX(1f)
                        .scaleY(1f)
                        .setDuration(ENTER_MS)
                        .setInterpolator(EASE_OUT)
                        .withLayer()
                        .withEndAction(() -> {
                            if (showing && !dismissing) {
                                scene.startAfterTransition();
                            }
                        })
                        .start();
                return true;
            }
        });
    }

    void dismiss(Runnable onDone) {
        if (!showing || dismissing) return;
        dismissing = true;
        cancelAnimations();
        scene.stop();

        Rect homeRect = homeClockRect(activeHomeClock);
        float targetScale = scaleToMatchHome(activeHomeClock);

        content.setPivotX(0f);
        content.setPivotY(0f);
        content.setAlpha(1f);

        if (activeHomeClockBlock != null) {
            activeHomeClockBlock.animate()
                    .alpha(1f)
                    .setStartDelay(EXIT_MS / 2)
                    .setDuration(EXIT_MS / 2)
                    .start();
        }
        backdrop.animate().alpha(0f).setDuration(EXIT_MS).setInterpolator(EASE_OUT).start();
        ambient.animate().alpha(0f).setDuration(EXIT_MS).setInterpolator(EASE_OUT).start();
        content.animate()
                .x(homeRect.left)
                .y(homeRect.top)
                .scaleX(targetScale)
                .scaleY(targetScale)
                .setDuration(EXIT_MS)
                .setInterpolator(EASE_OUT)
                .withLayer()
                .withEndAction(() -> {
                    if (activeHomeClockBlock != null) activeHomeClockBlock.setAlpha(1f);
                    if (overlay != null) overlay.setVisibility(View.GONE);
                    showing = false;
                    dismissing = false;
                    if (onDone != null) onDone.run();
                })
                .start();
    }

    void hideImmediate() {
        if (overlay == null) return;
        cancelAnimations();
        if (scene != null) scene.stop();
        if (activeHomeClockBlock != null) activeHomeClockBlock.setAlpha(1f);
        overlay.setVisibility(View.GONE);
        showing = false;
        dismissing = false;
    }

    private void ensureInflated() {
        if (overlay != null) return;
        overlay = LayoutInflater.from(root.getContext()).inflate(R.layout.dream_clock, root, false);
        overlay.setVisibility(View.GONE);
        root.addView(overlay);
        backdrop = overlay.findViewById(R.id.dreamBackdrop);
        ambient = overlay.findViewById(R.id.dreamAmbient);
        content = overlay.findViewById(R.id.dreamContent);
        dreamTime = overlay.findViewById(R.id.dreamTime);
        scene = new ClockScene(overlay);
    }

    private void cancelAnimations() {
        if (backdrop != null) backdrop.animate().cancel();
        if (ambient != null) ambient.animate().cancel();
        if (content != null) content.animate().cancel();
        if (activeHomeClockBlock != null) activeHomeClockBlock.animate().cancel();
    }

    private Rect homeClockRect(View homeClock) {
        Rect r = new Rect();
        if (homeClock != null && homeClock.isAttachedToWindow()) {
            root.offsetDescendantRectToMyCoords(homeClock, r);
        }
        return r;
    }

    private float scaleToMatchHome(View homeClock) {
        if (homeClock != null && dreamTime != null
                && homeClock.getHeight() > 0 && dreamTime.getHeight() > 0) {
            return (float) homeClock.getHeight() / dreamTime.getHeight();
        }
        return 52f / 120f;
    }
}
