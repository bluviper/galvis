package com.galvis.launcher.ui

import android.view.View
import android.view.animation.DecelerateInterpolator

object FocusScaleHelper {

    private val interpolator = DecelerateInterpolator(1.5f)
    private const val ANIMATION_DURATION_MS = 140L
    private const val FOCUSED_SCALE = 1.08f
    private const val UNFOCUSED_SCALE = 1.0f

    /**
     * Attaches smooth hardware-accelerated scaling and elevation to any TV tile on focus change.
     */
    fun attachFocusAnimation(
        view: View,
        onFocusChanged: ((isFocused: Boolean) -> Unit)? = null
    ) {
        view.setOnFocusChangeListener { v, hasFocus ->
            val targetScale = if (hasFocus) FOCUSED_SCALE else UNFOCUSED_SCALE
            val targetElevation = if (hasFocus) 12f else 0f

            v.animate()
                .scaleX(targetScale)
                .scaleY(targetScale)
                .translationZ(targetElevation)
                .setDuration(ANIMATION_DURATION_MS)
                .setInterpolator(interpolator)
                .start()

            onFocusChanged?.invoke(hasFocus)
        }
    }
}
