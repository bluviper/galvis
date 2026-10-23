package com.galvis.launcher.ui

import android.graphics.Color
import android.os.Build
import android.view.View
import android.view.animation.DecelerateInterpolator
import android.view.animation.OvershootInterpolator

object FocusScaleHelper {

    private val springInterpolator = OvershootInterpolator(1.35f)
    private val exitInterpolator = DecelerateInterpolator(1.5f)
    private const val ANIMATION_DURATION_MS = 160L
    private const val FOCUSED_SCALE = 1.10f
    private const val UNFOCUSED_SCALE = 1.0f

    /**
     * Attaches fluid hardware-accelerated spring physics and glowing elevation on focus change.
     */
    fun attachFocusAnimation(
        view: View,
        onFocusChanged: ((isFocused: Boolean) -> Unit)? = null
    ) {
        view.setOnFocusChangeListener { v, hasFocus ->
            val targetScale = if (hasFocus) FOCUSED_SCALE else UNFOCUSED_SCALE
            val targetElevation = if (hasFocus) 20f else 0f
            val interpolator = if (hasFocus) springInterpolator else exitInterpolator

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                if (hasFocus) {
                    v.outlineSpotShadowColor = Color.parseColor("#B364B5F6")
                    v.outlineAmbientShadowColor = Color.parseColor("#80000000")
                } else {
                    v.outlineSpotShadowColor = Color.TRANSPARENT
                    v.outlineAmbientShadowColor = Color.TRANSPARENT
                }
            }

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
