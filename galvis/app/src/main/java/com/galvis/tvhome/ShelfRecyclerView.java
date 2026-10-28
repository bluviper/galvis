package com.galvis.tvhome;

import android.content.Context;
import android.util.AttributeSet;
import android.view.FocusFinder;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

/**
 * Horizontal shelf RecyclerView that strictly traps horizontal focus navigation within the carousel.
 * Prevents Android's FocusFinder from escaping upward to the top nav buttons (btnWifi, btnSettings)
 * during fast scrolling or edge navigation.
 */
public class ShelfRecyclerView extends RecyclerView {

    private boolean rewinding = false;

    public ShelfRecyclerView(@NonNull Context context) {
        super(context);
    }

    public ShelfRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public ShelfRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setRewinding(boolean rewinding) {
        this.rewinding = rewinding;
    }

    public boolean isRewinding() {
        return rewinding;
    }

    @Override
    public void clearChildFocus(View child) {
        if (rewinding) {
            // While rewinding back to start, ignore clearChildFocus so Android's focus engine
            // doesn't prematurely dump focus onto the window root, top bar, or vertical grid.
            return;
        }
        super.clearChildFocus(child);
    }

    @Override
    public View focusSearch(View focused, int direction) {
        if (rewinding) {
            return focused != null ? focused : this;
        }
        View next = super.focusSearch(focused, direction);
        if (direction == View.FOCUS_LEFT || direction == View.FOCUS_RIGHT) {
            // Allow normal internal horizontal navigation and scrolling to off-screen cards,
            // but NEVER allow focus to escape outside this shelf to top nav buttons.
            if (next == null || !isDescendant(next)) {
                return focused != null ? focused : this;
            }
        }
        return next;
    }

    private boolean isDescendant(View v) {
        android.view.ViewParent p = v.getParent();
        while (p != null) {
            if (p == this) return true;
            p = p.getParent();
        }
        return false;
    }
}
