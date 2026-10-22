# Changelog

All notable changes to the **GalVis** project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.2.0] - 2026-10-23

### Added
- **Anchored Inputs Flyout Menu**: Replaced the default modal dialog with a frosted glass dropdown (`PopupWindow`) anchored directly underneath the `Inputs` header pill.
  - Displays hardware inputs: `HDMI 1`, `HDMI 2 ● Connected`, `HDMI 3`, `HDMI PlayStation 5 ● Connected`, `AV`, and `Live TV`.
  - Full D-pad keyboard/remote navigation with auto-focus on first item and back-key dismiss.
- **Full-Bleed Masked Cards**: Upgraded both dock tiles (`item_dock_app.xml`) and grid tiles (`item_grid_app.xml`) to `MaterialCardView` with `scaleType="centerCrop"` and zero inner padding.
  - App banners (such as YouTube, Play Store, Games) now fill 100% of the tile area, cleanly clipped to the 16dp rounded corner mask with zero black letterbox borders.
- **Smart Title Reveal System**:
  - Displays the active app name in bold 38sp font in the top shelf when browsing the dock band.
  - Automatically transitions to individual inline labels beneath tiles when browsing the lower grid.
- **4-Slot Dock Padding Engine**: The Favorites dock band dynamically ensures 4 pinned tiles are always present, padding with installed apps to preserve the Apple TV pill shelf proportions.

### Changed
- **Header Pill Focus Tinting**: Added `android:drawableTint="@color/pill_text_color"` and `android:drawableTintMode="src_in"` to all header actions (`Inputs`, `Wi-Fi`, `Settings`). Vector icons now synchronously invert to solid pitch-black (`#000000`) when focused on the white pill.
- **Vertical Spacing & Hierarchy**: Increased top console margin to 52dp to provide generous breathing room matching the CleanYourTV and tvOS visual rhythm.
- **Theme Foundation**: Upgraded `Theme.GalVis` to inherit from `Theme.MaterialComponents.NoActionBar` with dark ambient window flags for smooth, crash-free Material Card rendering.

---

## [0.1.0] - 2026-10-23

### Added
- **Core Architecture & Engine**: Native Kotlin + AndroidX Views stack targeting Android TV (API 26–34), specifically optimized for low-memory smart TVs (TCL 55C635 with 2 GB RAM and Cortex-A55 SoC).
- **Zero-Bloat App Discovery**: `AppRepository` querying `CATEGORY_LEANBACK_LAUNCHER`, standard sideloaded apps, and `TvInputManager` passthrough sources.
- **Dynamic Ambient Glow**: Hardware-accelerated radial gradient layer dynamically extracting dominant brand hues via AndroidX Palette (e.g. YouTube crimson red, Prime cyan, Netflix dark red).
- **D-Pad Focus Physics**: 140ms hardware-accelerated 1.08x scale zoom with smooth elevation and white rim highlights.
- **Minimalist Header Console**: Real-time tracked time, AM/PM, formatted date divider, and network status monitor (Ethernet / Wi-Fi).
