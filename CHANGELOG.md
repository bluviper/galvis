# Changelog

All notable changes to the **GalVis** project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.1.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

---

## [0.4.0] - 2026-10-23

### Added
- **Unified Engine: CleanYourTV Foundation Branded as GALVis™**:
  - Adopted the 474 KB ultra-low-footprint launcher engine as the primary core, eliminating framework overhead and achieving instant cold-start on the TCL 55C635 (2 GB RAM).
  - Renamed user-facing strings and launcher settings to **GALVis™**.
- **Signature "GALVis" Theme Mashup**:
  - **Glass Elements**: Frosted translucent dock (`dock_glass: true`, `dock_opacity: 70`) so wallpapers shine through; full dynamic per-app glow (`app_glow: true`, `ambient_glow: true`); and advanced card hover physics (`advanced_effects: true`).
  - **Ruska Atmosphere**: Twilight lake backdrop (`wallpaper: true`, `wallpaper_true_color: true`) over a deep midnight navy base (`#07080D`).
  - **Six-Column Grid**: Configured 6-item columns (`columns: 6`) with a bottom row peek (`rows_below_dock: 1`) for expansive app browsing.
  - **Minimalist Icon-Only Header**: Round frosted pill buttons with no text clutter (`icon_only_buttons: true`, `icon_only_inputs: true`) and real-time emerald Wi-Fi state (`wifi_status_color: true`).
  - **Fluid Spring Physics**: Maintained signature Apple TV spring bounce (`focus_overshoot: true`).

---

## [0.3.0] - 2026-10-23

### Added
- **CleanYourTV Hero Banner Parity**:
  - Aligned home layout with the hero banner specification from [cleanyour.tv](https://cleanyour.tv/).
  - **4-Column Lower App Grid**: Converted `rvAllApps` from 6 columns to 4 columns, matching the 4 cards in the Apple TV dock band with pixel-exact vertical column alignment.
  - **Full-Size Card Geometry Parity**: Standardized all grid cards to `180dp x 102dp` (16:9 aspect ratio) with `16dp` rounded corners, matching the dock cards.
  - **Centered Dock Band**: Re-anchored `dockBand` with symmetric start and end parent constraints, centering the dock band at horizontal center (960px on 1080p).
  - **Left-Aligned Shelf Title**: Constrained `tvFocusedTitle` to the start of `dockBand` with a 14dp inset, aligning with the left edge of the first dock card.
  - **Atmospheric Ambient Glow**: Expanded the ambient radial glow height to 500dp with a widened radius of 850f, producing a soft background haze matching CleanYourTV's `radial-gradient(55% 50% at 30% 42%, var(--glow, rgba(0,0,0,0)) 0%, rgba(0,0,0,0) 70%)`.
  - **Smart Tile Label Spacing**: Enhanced inline labels with 8dp top margin and crisp 13sp typography that reveals only when focused.

---

## [0.2.1] - 2026-10-23

### Added
- **Multi-Layer Floating Glass Highlight**:
  - Engineered a 4-layer composite highlight in `bg_tile_focus_rim.xml` matching CleanYourTV and Apple TV tvOS.
  - Layer 1: Contrast drop shadow perimeter ensuring crisp edge separation on pure white cards (e.g. YouTube).
  - Layer 2: Radiant ice-blue luminous glass bezel (`#F080D8FF`).
  - Layer 3: Crisp diamond-white core outline (`#FFFFFFFF`).
  - Layer 4: Specular glass glare gradient simulating polished curved glass sheen across the top 45% of the focused card.
- **Physical Spring D-Pad Physics**:
  - Upgraded `FocusScaleHelper.kt` to use `OvershootInterpolator(1.35f)` with 160ms timing for that signature Apple TV physical spring bounce.
  - Added hardware-accelerated luminous cyan spot shadow elevation (`outlineSpotShadowColor = #B364B5F6`, `translationZ = 20f`) on API 28+ devices.

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
