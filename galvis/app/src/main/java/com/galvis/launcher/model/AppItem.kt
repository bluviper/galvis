package com.galvis.launcher.model

import android.graphics.drawable.Drawable

/**
 * Immutable model representing an application or TV input tile in GalVis.
 */
data class AppItem(
    val label: String,
    val packageName: String,
    val activityName: String,
    val icon: Drawable,
    val isFavorite: Boolean = false,
    val isTvInput: Boolean = false,
    val inputId: String? = null
)
