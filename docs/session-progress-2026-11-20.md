# Session Progress & Handoff — November 19–20, 2026

**Project**: GALVis™ TV Home (`com.galvis.tvhome`)  
**Current Version**: `v1.1.0-beta` (`versionCode=2`, Commit `c389167`)  
**Target Device**: TCL 55C635 (`product:G03_4K_GB`, `model:Smart_TV_Pro`, `device:BeyondTV4`, `192.168.31.124:5555`)

---

## 1. Root Cause Analysis: In-App Settings Button Error

We revisited the remote shortcut fix from [`adbf845`](https://github.com/bluviper/galvis/commit/adbf845) (Nov 2, 2026), which mapped TCL remote shortcut keys in [`config.xml`](../galvis/app/src/main/res/values/config.xml) (`4062` Netflix, `4065` YouTube, `4099` Prime Video, and Settings keycodes `83`, `4020`, `4077`) with `com.galvis.tvhome/.InputKeyService` enabled on the TV.

- **Symptom:** The Settings button worked on the GALVis Home screen, but inside foreground apps like YouTube or Netflix it triggered a TCL/Google TV system error screen advising the user to contact TCL.
- **Cause:** `MainActivity.dispatchKeyEvent()` called `handleSettingsKey()`, whereas [`InputKeyService.onKeyEvent()`](../galvis/app/src/main/java/com/galvis/tvhome/InputKeyService.java) only handled `app_keys` and `input_key_keycodes` and returned `false` for `settings_key_keycodes`. Unhandled Settings presses fell through to `TclPhoneWindowManager` and the disabled `launcherx` fallback.
- **Settings Side-Sheet Behavior Verified:** Queried `android.settings.SETTINGS` on the TV and confirmed it resolves to `com.android.tv.settings/.MainSettings`, which natively opens as a translucent right-side overlay ("side-sheet"). Keeping `settings_key_panel` as `false` opens the full TV Settings page over YouTube while background video/audio playback continues (allowing access to TCL's **Audio Only / Turn off screen** option).

---

## 2. Changes Implemented & Committed

### 1. In-App Settings Interception & Accessibility Self-Repair (`1b4f515`)
- **[`InputKeyService.java`](../galvis/app/src/main/java/com/galvis/tvhome/InputKeyService.java)**:
  - Loaded `settings_key_scancodes`, `settings_key_keycodes`, and `settings_key_panel` in `onServiceConnected()`.
  - Added `isSettingsKey(KeyEvent)` and global Settings key handling in `onKeyEvent()` (launching `SystemSettings.intent(this, Settings.ACTION_SETTINGS)` when `settings_key_panel` is `false`, or `QuickSettingsActivity` when `true`).
- **[`ServiceRepair.java`](../galvis/app/src/main/java/com/galvis/tvhome/ServiceRepair.java)**:
  - Guarded against empty recorded service preferences and updated `restore()` to re-assert `ACCESSIBILITY_ENABLED = 1` whenever recorded helpers exist and Android reset the flag to `0`.
- **[`config.xml`](../galvis/app/src/main/res/values/config.xml)**:
  - Kept `settings_key_panel` set to `false` and enabled `restore_key_services` (`true`).

### 2. Idle Clock Morph Overlay, Wallpaper Diffuser & Build Config (`290b196`)
- Committed pending work across [`IdleClockOverlay.java`](../galvis/app/src/main/java/com/galvis/tvhome/IdleClockOverlay.java), [`ClockScene.java`](../galvis/app/src/main/java/com/galvis/tvhome/ClockScene.java), [`MainActivity.java`](../galvis/app/src/main/java/com/galvis/tvhome/MainActivity.java), [`WallpaperPlayer.java`](../galvis/app/src/main/java/com/galvis/tvhome/WallpaperPlayer.java), [`dream_clock.xml`](../galvis/app/src/main/res/layout/dream_clock.xml), and [`config.xml`](../galvis/app/src/main/res/values/config.xml) (`home_idle_clock_minutes=9`, `home_idle_clock_seconds=0`).
- Updated Gradle wrapper / AGP (`8.13` / `8.13.2`) in [`build.gradle`](../galvis/build.gradle), [`settings.gradle`](../galvis/settings.gradle), and [`gradle-wrapper.properties`](../galvis/gradle/wrapper/gradle-wrapper.properties), plus voice/RAM trade-off docs in [`README.md`](../README.md), [`docs/beta-testing-and-stress-test.md`](beta-testing-and-stress-test.md), and [`xda_launch_kit.md`](../xda_launch_kit.md).

### 3. XDA Launch Post Updates & Release Documentation (`c389167`)
- Added a bordered `[TABLE][TR][TD]` **UPDATES & CHANGELOG** BBCode box with commit dates, hashes, and problem-solved summaries to both [`xda_launch_kit.md`](../xda_launch_kit.md) and [`docs/xda_launch_kit.md`](xda_launch_kit.md).
- Updated [`CHANGELOG.md`](../CHANGELOG.md), [`docs/release-notes-v1.1.0-beta.md`](release-notes-v1.1.0-beta.md), and [`docs/proper-releasenotes-codes-github.txt`](proper-releasenotes-codes-github.txt) with the Nov 2026 changes, known hardware notes, and updated APK build info.

---

## 3. Build, TV Deployment & GitHub Release Verification

- **Unit Tests & APK Build:** Ran `galvis\gradlew.bat -p galvis testDebugUnitTest assembleDebug assembleRelease` — `BUILD SUCCESSFUL` (28 test suites, 193 tests, 0 failures, 0 errors, 0 skipped).
- **Physical TV Deployment (`192.168.31.124:5555`):**
  - Installed `galvis/app/build/outputs/apk/release/app-release.apk` (`Success`).
  - Granted `WRITE_SECURE_SETTINGS` and `ACCESS_FINE_LOCATION` (`granted=true`).
  - Compiled `com.galvis.tvhome` (`cmd package compile -m speed -f`, `Success`).
  - Re-bound `com.galvis.tvhome/.InputKeyService` and confirmed via `dumpsys accessibility` that `InputKeyService` is in `Enabled services` and `Bound services` with `accessibility_enabled=1` and `Crashed services: []`.
- **GitHub Release (`v1.1.0-beta`):**
  - Force-updated tag `v1.1.0-beta` to `c389167` and pushed to `origin`.
  - Updated GitHub Release `v1.1.0-beta` (`id=428603616`) release notes and replaced `app-release.apk` (`id=690707516` deleted; new asset uploaded).
  - **Published Asset:** `app-release.apk` (`3,127,741` bytes, `state: uploaded`)
  - **SHA-256:** `D5A4379D8B4FAAA6CD95798A611DA96DF1519EA41F4B40C9AE78EF89036921D3`

---

## 4. Next Session Follow-Up

- [ ] **Live Remote Confirmation:** Confirm on the TCL 55C635 with the physical remote that pressing **Settings** inside YouTube/Netflix opens `com.android.tv.settings/.MainSettings` cleanly while playback continues, and verify **Audio Only** screen-off navigation.
- [ ] **Untracked Root Screenshots:** Decide whether to remove or ignore the untracked screenshots (`app_running.png`, `emulator_screen.png`, `screenshot1.png`) in the repo root.
