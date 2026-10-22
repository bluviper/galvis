package com.galvis.launcher.data

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.media.tv.TvInputInfo
import android.media.tv.TvInputManager
import androidx.core.content.ContextCompat
import com.galvis.launcher.R
import com.galvis.launcher.model.AppItem
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AppRepository(private val context: Context) {

    private val prefs = context.getSharedPreferences("galvis_launcher_prefs", Context.MODE_PRIVATE)
    private val keyFavorites = "key_pinned_favorites"

    fun getFavoritePackageNames(): Set<String> {
        return prefs.getStringSet(keyFavorites, emptySet()) ?: emptySet()
    }

    fun setFavorite(packageName: String, isFavorite: Boolean) {
        val current = getFavoritePackageNames().toMutableSet()
        if (isFavorite) {
            current.add(packageName)
        } else {
            current.remove(packageName)
        }
        prefs.edit().putStringSet(keyFavorites, current).apply()
    }

    /**
     * Loads installed TV apps, sideloaded applications, and hardware TV inputs (HDMI 1, 2, etc.)
     */
    suspend fun loadAllItems(): Pair<List<AppItem>, List<AppItem>> = withContext(Dispatchers.IO) {
        val pm = context.packageManager
        val favorites = getFavoritePackageNames()
        val appMap = LinkedHashMap<String, AppItem>()

        // 1. Discover Leanback TV Apps
        val leanbackIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LEANBACK_LAUNCHER)
        val leanbackApps = pm.queryIntentActivities(leanbackIntent, PackageManager.MATCH_ALL)
        for (resolveInfo in leanbackApps) {
            val item = resolveInfoToAppItem(pm, resolveInfo, favorites)
            if (item != null && item.packageName != context.packageName) {
                appMap[item.packageName] = item
            }
        }

        // 2. Discover Sideloaded / Standard Android Apps (captures non-leanback APKs like SmartTube if needed)
        val standardIntent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
        val standardApps = pm.queryIntentActivities(standardIntent, PackageManager.MATCH_ALL)
        for (resolveInfo in standardApps) {
            val item = resolveInfoToAppItem(pm, resolveInfo, favorites)
            if (item != null && item.packageName != context.packageName && !appMap.containsKey(item.packageName)) {
                appMap[item.packageName] = item
            }
        }

        // 3. Query Hardware TV Inputs (HDMI 1, HDMI 2, Tuner)
        val tvInputItems = queryHardwareTvInputs()

        // Combine inputs and apps
        val allAppsList = (tvInputItems + appMap.values.sortedBy { it.label.lowercase() })

        // Split into Pinned Dock (4 Favorites, Apple TV style) and All Apps Grid
        val favoriteApps = allAppsList.filter { it.isFavorite }.toMutableList()
        if (favoriteApps.size < 4) {
            for (app in allAppsList) {
                if (!favoriteApps.any { it.packageName == app.packageName && it.inputId == app.inputId }) {
                    favoriteApps.add(app)
                    if (favoriteApps.size == 4) break
                }
            }
        }
        val dockList = favoriteApps.take(4)

        return@withContext Pair(dockList, allAppsList)
    }

    private fun resolveInfoToAppItem(
        pm: PackageManager,
        resolveInfo: ResolveInfo,
        favorites: Set<String>
    ): AppItem? {
        val activityInfo = resolveInfo.activityInfo ?: return null
        val pkg = activityInfo.packageName
        val label = resolveInfo.loadLabel(pm).toString()

        // High-DPI Icon Resolution: Prefer TV Banner or highest density icon
        val icon = activityInfo.loadBanner(pm)
            ?: activityInfo.applicationInfo?.loadBanner(pm)
            ?: getHighResIcon(pm, activityInfo)
            ?: resolveInfo.loadIcon(pm)

        return AppItem(
            label = label,
            packageName = pkg,
            activityName = activityInfo.name,
            icon = icon,
            isFavorite = favorites.contains(pkg)
        )
    }

    private fun getHighResIcon(pm: PackageManager, activityInfo: android.content.pm.ActivityInfo): android.graphics.drawable.Drawable? {
        return try {
            val res = pm.getResourcesForApplication(activityInfo.applicationInfo)
            val iconResId = if (activityInfo.icon != 0) activityInfo.icon else activityInfo.applicationInfo.icon
            if (iconResId != 0) {
                // Request XXXHDPI / XXHDPI density (480–640 DPI) for crystal clarity on 55" 4K displays
                res.getDrawableForDensity(iconResId, android.util.DisplayMetrics.DENSITY_XXXHIGH, null)
                    ?: res.getDrawableForDensity(iconResId, android.util.DisplayMetrics.DENSITY_XXHIGH, null)
            } else null
        } catch (_: Exception) {
            null
        }
    }

    private fun queryHardwareTvInputs(): List<AppItem> {
        val inputs = mutableListOf<AppItem>()
        val tvManager = context.getSystemService(Context.TV_INPUT_SERVICE) as? TvInputManager ?: return inputs

        try {
            val inputList = tvManager.tvInputList
            for (info in inputList) {
                if (info.isPassthroughInput) {
                    val label = when (info.type) {
                        TvInputInfo.TYPE_HDMI -> "HDMI (${info.id.substringAfterLast("/")})"
                        TvInputInfo.TYPE_TUNER -> "Live TV"
                        TvInputInfo.TYPE_COMPOSITE -> "AV / Composite"
                        else -> "Input"
                    }
                    val icon = ContextCompat.getDrawable(context, R.drawable.ic_galvis_banner) ?: return inputs

                    inputs.add(
                        AppItem(
                            label = label,
                            packageName = "com.android.tv",
                            activityName = "TvInputActivity",
                            icon = icon,
                            isFavorite = true, // Pin HDMI by default
                            isTvInput = true,
                            inputId = info.id
                        )
                    )
                }
            }
        } catch (_: Exception) {
            // Ignore if TV input manager is restricted on this build
        }

        return inputs
    }
}
