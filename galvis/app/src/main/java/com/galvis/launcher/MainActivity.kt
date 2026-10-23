package com.galvis.launcher

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.BroadcastReceiver
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.media.tv.TvContract
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.view.View
import android.view.ViewGroup
import android.widget.PopupWindow
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.ColorUtils
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.galvis.launcher.adapter.AppAdapter
import com.galvis.launcher.data.AppRepository
import com.galvis.launcher.databinding.ActivityMainBinding
import com.galvis.launcher.model.AppItem
import com.galvis.launcher.ui.FocusScaleHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var repository: AppRepository

    private lateinit var dockAdapter: AppAdapter
    private lateinit var gridAdapter: AppAdapter

    private var currentGlowColor: Int = Color.parseColor("#081220")

    private val packageReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            loadApplications()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        repository = AppRepository(this)
        setupViews()
        setupHeaderActions()
        setupBackNavigation()
        updateClockAndDate()
        updateNetworkStatus()
        registerPackageChangeReceiver()

        loadApplications()
    }

    override fun onResume() {
        super.onResume()
        updateClockAndDate()
        updateNetworkStatus()
    }

    private var inputsPopup: PopupWindow? = null

    override fun onDestroy() {
        super.onDestroy()
        inputsPopup?.dismiss()
        inputsPopup = null
        unregisterReceiver(packageReceiver)
    }

    private fun setupViews() {
        // 4-Card Apple TV Frosted Dock
        dockAdapter = AppAdapter(
            layoutResId = R.layout.item_dock_app,
            onItemClick = { app -> launchItem(app) },
            onItemLongClick = { app -> toggleFavorite(app) },
            onItemFocused = { app, isFocused -> onAppFocused(app, isFocused, isDock = true) }
        )
        binding.rvDock.apply {
            layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            adapter = dockAdapter
        }

        // All Apps Lower Grid (4 Columns, Aligned with Apple TV Dock Cards)
        gridAdapter = AppAdapter(
            layoutResId = R.layout.item_grid_app,
            onItemClick = { app -> launchItem(app) },
            onItemLongClick = { app -> toggleFavorite(app) },
            onItemFocused = { app, isFocused -> onAppFocused(app, isFocused, isDock = false) }
        )
        binding.rvAllApps.apply {
            layoutManager = GridLayoutManager(this@MainActivity, 4)
            adapter = gridAdapter
        }
    }

    private fun setupHeaderActions() {
        FocusScaleHelper.attachFocusAnimation(binding.btnInputs)
        FocusScaleHelper.attachFocusAnimation(binding.btnNetwork)
        FocusScaleHelper.attachFocusAnimation(binding.btnSettings)

        // Frosted Inputs Dropdown (HDMI 1, HDMI 2, AV, Live TV)
        binding.btnInputs.setOnClickListener {
            showInputsMenu()
        }

        // Frosted Network Button (Wi-Fi Settings)
        binding.btnNetwork.setOnClickListener {
            try {
                startActivity(Intent(Settings.ACTION_WIFI_SETTINGS))
            } catch (_: Exception) {
                startActivity(Intent(Settings.ACTION_WIRELESS_SETTINGS))
            }
        }

        // Frosted Settings Button
        binding.btnSettings.setOnClickListener {
            try {
                startActivity(Intent(Settings.ACTION_SETTINGS))
            } catch (_: Exception) {
                Toast.makeText(this, "Could not open Settings", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showInputsMenu() {
        if (inputsPopup?.isShowing == true) {
            inputsPopup?.dismiss()
            return
        }

        val popupView = layoutInflater.inflate(R.layout.popup_inputs_menu, null)
        val popup = PopupWindow(
            popupView,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT,
            true
        ).apply {
            isOutsideTouchable = true
            elevation = 24f
            setOnDismissListener {
                inputsPopup = null
            }
        }
        inputsPopup = popup

        popupView.findViewById<View>(R.id.menuItemHdmi1)?.setOnClickListener {
            tuneInput("HDMI 1")
            popup.dismiss()
        }
        popupView.findViewById<View>(R.id.menuItemHdmi2)?.setOnClickListener {
            tuneInput("HDMI 2")
            popup.dismiss()
        }
        popupView.findViewById<View>(R.id.menuItemHdmi3)?.setOnClickListener {
            tuneInput("HDMI 3")
            popup.dismiss()
        }
        popupView.findViewById<View>(R.id.menuItemHdmiPs5)?.setOnClickListener {
            tuneInput("HDMI 4")
            popup.dismiss()
        }
        popupView.findViewById<View>(R.id.menuItemAv)?.setOnClickListener {
            tuneInput("AV")
            popup.dismiss()
        }
        popupView.findViewById<View>(R.id.menuItemLiveTv)?.setOnClickListener {
            launchLiveTv()
            popup.dismiss()
        }

        // Anchor directly below btnInputs, exactly as in CleanYourTV
        popup.showAsDropDown(binding.btnInputs, 0, 10)

        // Auto-focus first entry so D-pad works immediately
        popupView.post {
            popupView.findViewById<View>(R.id.menuItemHdmi1)?.requestFocus()
        }
    }

    private fun tuneInput(inputName: String) {
        try {
            val intent = Intent(Intent.ACTION_VIEW).apply {
                data = Uri.parse("content://android.media.tv/passthrough")
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(intent)
        } catch (_: Exception) {
            Toast.makeText(this, "Switched to $inputName", Toast.LENGTH_SHORT).show()
        }
    }

    private fun launchLiveTv() {
        try {
            val tvIntent = Intent(Intent.ACTION_VIEW, TvContract.Channels.CONTENT_URI).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
            startActivity(tvIntent)
        } catch (_: Exception) {
            Toast.makeText(this, "Live TV", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupBackNavigation() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (inputsPopup?.isShowing == true) {
                    inputsPopup?.dismiss()
                    binding.btnInputs.requestFocus()
                    return
                }
                if (binding.rvDock.findFocus() == null && dockAdapter.itemCount > 0) {
                    binding.rvDock.post {
                        binding.rvDock.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                    }
                }
            }
        })
    }

    private fun updateClockAndDate() {
        val now = Calendar.getInstance()
        val hour = now.get(Calendar.HOUR_OF_DAY)
        val greeting = when (hour) {
            in 5..11 -> "GOOD MORNING"
            in 12..16 -> "GOOD AFTERNOON"
            else -> "GOOD EVENING"
        }
        binding.tvGreeting.text = greeting

        // Time (12-hour format e.g. "5:35")
        val timeFormat = SimpleDateFormat("h:mm", Locale.getDefault())
        binding.tvTime.text = timeFormat.format(now.time)

        // AM/PM
        val amPmFormat = SimpleDateFormat("a", Locale.getDefault())
        binding.tvAmPm.text = amPmFormat.format(now.time).uppercase()

        // All-caps tracked Date (e.g. "MONDAY 19 OCTOBER")
        val dateFormat = SimpleDateFormat("EEEE d MMMM", Locale.getDefault())
        binding.tvDate.text = dateFormat.format(now.time).uppercase()
    }

    private fun updateNetworkStatus() {
        try {
            val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val network = cm.activeNetwork
            val capabilities = cm.getNetworkCapabilities(network)

            when {
                capabilities == null -> {
                    binding.btnNetwork.text = "Offline"
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> {
                    binding.btnNetwork.text = "Ethernet"
                }
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> {
                    binding.btnNetwork.text = "Wi-Fi"
                }
                else -> {
                    binding.btnNetwork.text = "Connected"
                }
            }
        } catch (_: Exception) {
            binding.btnNetwork.text = "Wi-Fi"
        }
    }

    private fun onAppFocused(app: AppItem, isFocused: Boolean, isDock: Boolean) {
        if (isFocused) {
            if (isDock) {
                binding.tvFocusedTitle.visibility = View.VISIBLE
                binding.tvFocusedTitle.text = app.label
            } else {
                binding.tvFocusedTitle.visibility = View.INVISIBLE
            }
            animateAmbientGlow(app)
        }
    }

    private fun animateAmbientGlow(app: AppItem) {
        lifecycleScope.launch(Dispatchers.Default) {
            val targetColor = extractDominantColor(app)
            withContext(Dispatchers.Main) {
                val animator = ValueAnimator.ofObject(ArgbEvaluator(), currentGlowColor, targetColor)
                animator.duration = 260
                animator.addUpdateListener { va ->
                    val color = va.animatedValue as Int
                    currentGlowColor = color
                    applyGlowColor(color)
                }
                animator.start()
            }
        }
    }

    private fun applyGlowColor(color: Int) {
        val semiTransparent = ColorUtils.setAlphaComponent(color, 135)
        val transparent = ColorUtils.setAlphaComponent(color, 0)

        val gradient = GradientDrawable(
            GradientDrawable.Orientation.TL_BR,
            intArrayOf(semiTransparent, transparent)
        ).apply {
            gradientType = GradientDrawable.RADIAL_GRADIENT
            gradientRadius = 850f
            setGradientCenter(0.35f, 0.35f)
        }
        binding.ivAmbientGlow.setImageDrawable(gradient)
    }

    private fun extractDominantColor(app: AppItem): Int {
        val pkg = app.packageName.lowercase()
        val label = app.label.lowercase()

        return when {
            pkg.contains("youtube") || label.contains("youtube") -> Color.parseColor("#E62117")
            pkg.contains("netflix") || label.contains("netflix") -> Color.parseColor("#E50914")
            pkg.contains("prime") || label.contains("prime") -> Color.parseColor("#00A8E1")
            pkg.contains("spotify") || label.contains("spotify") -> Color.parseColor("#1DB954")
            pkg.contains("disney") || label.contains("hotstar") -> Color.parseColor("#113CCF")
            pkg.contains("fancode") || label.contains("fancode") -> Color.parseColor("#FF6D00")
            pkg.contains("plex") || label.contains("plex") -> Color.parseColor("#E5A00D")
            pkg.contains("play.games") -> Color.parseColor("#00C853")
            pkg.contains("live.tv") || app.isTvInput -> Color.parseColor("#0A84FF")
            pkg.contains("settings") -> Color.parseColor("#4A5568")
            else -> {
                try {
                    val bitmap = drawableToBitmap(app.icon)
                    val palette = Palette.from(bitmap).maximumColorCount(8).generate()
                    palette.getVibrantColor(palette.getDominantColor(Color.parseColor("#1A2332")))
                } catch (_: Exception) {
                    Color.parseColor("#1A2332")
                }
            }
        }
    }

    private fun drawableToBitmap(drawable: Drawable): Bitmap {
        if (drawable is BitmapDrawable && drawable.bitmap != null) {
            return drawable.bitmap
        }
        val width = if (drawable.intrinsicWidth > 0) drawable.intrinsicWidth else 96
        val height = if (drawable.intrinsicHeight > 0) drawable.intrinsicHeight else 96
        val bitmap = Bitmap.createBitmap(width.coerceAtMost(96), height.coerceAtMost(96), Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        drawable.setBounds(0, 0, canvas.width, canvas.height)
        drawable.draw(canvas)
        return bitmap
    }

    private fun loadApplications() {
        lifecycleScope.launch {
            val (favorites, allApps) = repository.loadAllItems()
            dockAdapter.submitList(favorites)
            gridAdapter.submitList(allApps)

            if (binding.root.findFocus() == null && favorites.isNotEmpty()) {
                binding.rvDock.post {
                    binding.rvDock.findViewHolderForAdapterPosition(0)?.itemView?.requestFocus()
                }
            }
        }
    }

    private fun launchItem(item: AppItem) {
        try {
            if (item.isTvInput && item.inputId != null) {
                val uri = TvContract.buildChannelUriForPassthroughInput(item.inputId)
                val tuneIntent = Intent(Intent.ACTION_VIEW, uri).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(tuneIntent)
                return
            }

            val pm = packageManager
            val launchIntent = pm.getLeanbackLaunchIntentForPackage(item.packageName)
                ?: pm.getLaunchIntentForPackage(item.packageName)

            if (launchIntent != null) {
                launchIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(launchIntent)
            } else {
                val explicitIntent = Intent().apply {
                    component = ComponentName(item.packageName, item.activityName)
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK
                }
                startActivity(explicitIntent)
            }
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to launch ${item.label}", Toast.LENGTH_SHORT).show()
        }
    }

    private fun toggleFavorite(item: AppItem): Boolean {
        val newStatus = !item.isFavorite
        repository.setFavorite(item.packageName, newStatus)
        Toast.makeText(
            this,
            if (newStatus) "Pinned ${item.label} to Favorites" else "Removed ${item.label} from Favorites",
            Toast.LENGTH_SHORT
        ).show()
        loadApplications()
        return true
    }

    private fun registerPackageChangeReceiver() {
        val filter = IntentFilter().apply {
            addAction(Intent.ACTION_PACKAGE_ADDED)
            addAction(Intent.ACTION_PACKAGE_REMOVED)
            addAction(Intent.ACTION_PACKAGE_REPLACED)
            addDataScheme("package")
        }
        registerReceiver(packageReceiver, filter)
    }
}
