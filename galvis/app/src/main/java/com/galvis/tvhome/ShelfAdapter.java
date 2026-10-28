package com.galvis.tvhome;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.graphics.Outline;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewOutlineProvider;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ShelfAdapter extends RecyclerView.Adapter<ShelfAdapter.ViewHolder> {

    public interface ShelfListener {
        void onShelfCardFocused(View card, Shelf.Item item, int shelfIndex);
        void onShelfCardClick(View card, Shelf.Item item);
        void onShelfCardLongClick(View card, Shelf.Item item);
        boolean onShelfKey(int shelfIndex, int cardIndex, int itemCount, int keyCode);
    }

    private final Context ctx;
    private List<Shelf.Item> items;
    private int shelfIndex;
    private final ShelfListener listener;
    private final float density;
    private final Map<String, Drawable> iconCache = new HashMap<>();
    private RecyclerView attachedRecyclerView;

    public ShelfAdapter(Context ctx, List<Shelf.Item> items, int shelfIndex, ShelfListener listener) {
        this.ctx = ctx;
        this.items = items;
        this.shelfIndex = shelfIndex;
        this.listener = listener;
        this.density = ctx.getResources().getDisplayMetrics().density;
    }

    @Override
    public void onAttachedToRecyclerView(@NonNull RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        this.attachedRecyclerView = recyclerView;
    }

    public void rewindToStart() {
        if (attachedRecyclerView == null) return;

        RecyclerView.LayoutManager lm = attachedRecyclerView.getLayoutManager();
        if (lm instanceof LinearLayoutManager) {
            LinearLayoutManager llm = (LinearLayoutManager) lm;
            // Only focus card 0 directly if it is already fully visible at its home position
            if (llm.findFirstCompletelyVisibleItemPosition() == 0) {
                RecyclerView.ViewHolder vh0 = attachedRecyclerView.findViewHolderForAdapterPosition(0);
                if (vh0 != null && vh0.itemView != null) {
                    View card = vh0.itemView.findViewById(R.id.cardContainer);
                    if (card != null) {
                        card.requestFocus();
                        return;
                    }
                }
            }
        }

        // If card 0 is off-screen (scrolled carousel): trap focus in the ShelfRecyclerView
        // so Android's framework never throws focus up to top nav buttons (btnWifi/btnSettings).
        if (attachedRecyclerView instanceof ShelfRecyclerView) {
            ((ShelfRecyclerView) attachedRecyclerView).setRewinding(true);
        }

        if (lm != null) {
            LinearSmoothScroller scroller = new LinearSmoothScroller(attachedRecyclerView.getContext()) {
                @Override
                protected int getHorizontalSnapPreference() {
                    return SNAP_TO_START;
                }

                @Override
                protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                    // Smooth, tape-like rewind speed (~280ms per inch -> ~350ms total for 400px)
                    return 280f / displayMetrics.densityDpi;
                }

                @Override
                protected void onStop() {
                    super.onStop();
                    if (attachedRecyclerView instanceof ShelfRecyclerView) {
                        ((ShelfRecyclerView) attachedRecyclerView).setRewinding(false);
                    }
                    RecyclerView.ViewHolder vh = attachedRecyclerView.findViewHolderForAdapterPosition(0);
                    if (vh != null && vh.itemView != null) {
                        View card = vh.itemView.findViewById(R.id.cardContainer);
                        if (card != null) {
                            card.requestFocus();
                            return;
                        }
                    }
                    if (attachedRecyclerView.getChildCount() > 0) {
                        View firstChild = attachedRecyclerView.getChildAt(0);
                        if (firstChild != null) {
                            View card = firstChild.findViewById(R.id.cardContainer);
                            if (card != null) card.requestFocus();
                        }
                    }
                }
            };
            scroller.setTargetPosition(0);
            lm.startSmoothScroll(scroller);
        }
    }

    public void update(List<Shelf.Item> newItems, int newShelfIndex) {
        this.items = newItems;
        this.shelfIndex = newShelfIndex;
        notifyDataSetChanged();
    }

    private Drawable getAppIcon(String pkg) {
        if (pkg == null) return null;
        if (iconCache.containsKey(pkg)) return iconCache.get(pkg);
        Drawable icon = null;
        try {
            icon = ctx.getPackageManager().getApplicationIcon(pkg);
        } catch (Exception ignored) {}
        iconCache.put(pkg, icon);
        return icon;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(ctx).inflate(R.layout.item_shelf_card, parent, false);
        return new ViewHolder(v, density);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder h, int position) {
        Shelf.Item item = items.get(position);

        if (item.appEntry != null) {
            // App Tile in "All Apps" Shelf (180dp x 101dp, matching Favorites Bar)
            h.badge.setVisibility(View.GONE);
            h.subtitle.setVisibility(View.GONE);
            h.brandLogo.setVisibility(View.GONE);

            if (item.appEntry.banner != null) {
                h.banner.setImageDrawable(item.appEntry.banner);
                h.banner.setVisibility(View.VISIBLE);
                h.icon.setVisibility(View.GONE);
                h.title.setVisibility(View.GONE);
            } else {
                h.banner.setVisibility(View.GONE);
                h.icon.setImageDrawable(item.appEntry.icon);
                h.icon.setVisibility(View.VISIBLE);
                h.title.setVisibility(View.GONE);
            }
        } else {
            // Content Shelf (Netflix, YouTube, Disney+)
            h.banner.setVisibility(View.GONE);
            h.icon.setVisibility(View.GONE);

            h.badge.setVisibility(View.VISIBLE);
            h.badge.setText(item.badge);
            h.badge.setTextColor(item.badgeColor);

            h.title.setText(item.title);
            h.title.setVisibility(View.VISIBLE);

            h.subtitle.setText(item.subtitle);
            h.subtitle.setVisibility(View.VISIBLE);

            Drawable brandIcon = getAppIcon(item.packageName);
            if (brandIcon != null) {
                h.brandLogo.setImageDrawable(brandIcon);
                h.brandLogo.setVisibility(View.VISIBLE);
            } else {
                h.brandLogo.setVisibility(View.GONE);
            }
        }

        h.bottomTitle.setText(item.title);
        h.bottomTitle.setAlpha(h.container.isFocused() ? 1f : 0f);

        h.container.setOnClickListener(v -> {
            if (listener != null) {
                listener.onShelfCardClick(v, item);
            }
        });

        h.container.setOnLongClickListener(v -> {
            if (listener != null) {
                listener.onShelfCardLongClick(v, item);
                return true;
            }
            return false;
        });

        h.container.setOnKeyListener((v, keyCode, event) -> {
            if (event.getAction() != KeyEvent.ACTION_DOWN) return false;
            int pos = h.getBindingAdapterPosition();
            if (pos == RecyclerView.NO_POSITION) return false;

            if (listener != null) {
                return listener.onShelfKey(shelfIndex, pos, getItemCount(), keyCode);
            }
            return false;
        });

        h.container.setOnFocusChangeListener((v, focused) -> {
            float scale = focused ? 1.08f : 1f;
            float tz = focused ? 8f * density : 0f;
            v.animate().scaleX(scale).scaleY(scale).translationZ(tz).setDuration(160).start();
            h.bottomTitle.animate().alpha(focused ? 1f : 0f).setDuration(160).start();
            if (focused && listener != null) {
                v.post(() -> {
                    if (v.isFocused()) {
                        listener.onShelfCardFocused(v, item, shelfIndex);
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    static final class ViewHolder extends RecyclerView.ViewHolder {
        final FrameLayout container;
        final ImageView banner;
        final ImageView icon;
        final ImageView brandLogo;
        final TextView badge;
        final TextView title;
        final TextView subtitle;
        final TextView bottomTitle;

        ViewHolder(View v, float density) {
            super(v);
            container = v.findViewById(R.id.cardContainer);
            banner = v.findViewById(R.id.cardBanner);
            icon = v.findViewById(R.id.cardIcon);
            brandLogo = v.findViewById(R.id.cardBrandLogo);
            badge = v.findViewById(R.id.cardBadge);
            title = v.findViewById(R.id.cardTitle);
            subtitle = v.findViewById(R.id.cardSubtitle);
            bottomTitle = v.findViewById(R.id.cardBottomTitle);

            container.setOutlineProvider(new ViewOutlineProvider() {
                @Override
                public void getOutline(View view, Outline outline) {
                    outline.setRoundRect(0, 0, view.getWidth(), view.getHeight(), 16f * density);
                }
            });
            container.setClipToOutline(true);
        }
    }
}
