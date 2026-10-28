# Session Progress & Handoff — October 28–29, 2026

**Project**: GALVis™ TV Home (`com.galvis.tvhome`)  
**Current Version**: `v1.1.0` (Commit `07f4632`)  
**Target Devices**: TCL 55C635 (Physical Android 11 Google TV) & `emulator-5554` (Android 14 ATV 1080p)

---

## 1. CleanYourTV v0.4.6 Analysis & Comparison

We evaluated the upstream community release [udhaykumarbala/cleanyourtv-app v0.4.6](https://github.com/udhaykumarbala/cleanyourtv-app/releases/tag/v0.4.6) (`cleanyourtv-0.4.6.apk`):

- **Architectural Difference**:
  - `cleanyourtv-0.4.6.apk` (7.22 MB) is an **Android phone companion app** (Kotlin + Jetpack Compose) that connects to TVs over local Wi-Fi ADB (`:5555`) to run debloat scripts and push a bundled launcher (`assets/bundled/cleanyourtv-launcher.apk`, 540 KB).
  - **GALVis™ (`com.galvis.tvhome`)** is a **standalone, native Android TV launcher APK** installed directly on the television with zero phone dependency, self-contained 1080p wallpapers, custom horizontal shelf tape-rewind physics, and on-TV frosted flydown menus.
- **Test Pool Findings** ([udhaykumarbala.github.io/cleanyourtv-app/testers](https://udhaykumarbala.github.io/cleanyourtv-app/testers/)):
  - **66 registered community testers** across **50+ TV models** and **17+ brands** (53 active in the past week).
  - **10+ OEM bugs caught by testers**: Xiaomi PatchWall remote key breakage, Philips cold-boot launcher reversion, Sony Bravia Android 9 ADB drops, 4K screenshot (>2 MB) safety abort, and Walton dock glitches.
  - **Key Insight Adopted**: Frictionless one-step issue reporting with pre-filled device telemetry dramatically increases tester feedback quality.

---

## 2. Licensing & Legal Verification

- Confirmed that the underlying CleanYourTV launcher components are licensed under the permissive **MIT License** ([`cleanyourtv_ref/LICENSE`](../cleanyourtv_ref/LICENSE)), which explicitly permits modification, custom branding, and distribution (including Google Play Store) provided the original copyright and permission notice are retained.
- Verified that [`galvis/LICENSE`](../galvis/LICENSE) properly includes the full MIT copyright attribution to Gathin & CleanYourTV contributors alongside the SIL Open Font License 1.1 for the Outfit font.
- Confirmed that no code or assets were copied from `cleanyourtv-0.4.6.apk`.

---

## 3. Feature Implemented: "Report an Issue · Tester Feedback" (`v1.1.0`)

To streamline bug reporting on a 10-foot TV UI without forcing testers to type on an on-screen remote keyboard, we built and integrated a native QR + telemetry modal:

### Core Components Added
1. **[`ReportIssueDialog.java`](../galvis/app/src/main/java/com/galvis/tvhome/ReportIssueDialog.java)**:
   - Collects live TV hardware diagnostics: Manufacturer & Model, Android OS & API level, Display resolution & DPI, Available/Total RAM, Java Heap usage, Build ID, and Fingerprint.
   - Encodes a pre-populated GitHub issue URL (`title` + Markdown `body`) into a crisp $400\times400$ QR code via ZXing (`com.google.zxing:core:3.5.3`).
   - Provides three D-pad actions: **Open on TV** (`ACTION_VIEW`), **Copy link & specs** (`ClipboardManager`), and **Close**.
2. **[`dialog_report_issue.xml`](../galvis/app/src/main/res/layout/dialog_report_issue.xml)** & **[`qr_background.xml`](../galvis/app/src/main/res/drawable/qr_background.xml)** & **[`ic_bug_report.xml`](../galvis/app/src/main/res/drawable/ic_bug_report.xml)**:
   - Styled with GALVis's `@drawable/glass_panel` and `@style/TopButton` pills.
3. **3 Launcher Menu Entry Points**:
   - [`LauncherSettingsActivity.java`](../galvis/app/src/main/java/com/galvis/tvhome/LauncherSettingsActivity.java) (`reportIssueChoice` row)
   - [`MainActivity.java`](../galvis/app/src/main/java/com/galvis/tvhome/MainActivity.java) (`showSettingsMenu()` and `showTopMenu()`)
   - [`QuickSettingsActivity.java`](../galvis/app/src/main/java/com/galvis/tvhome/QuickSettingsActivity.java) (Remote Settings key overlay)
4. **Unit Testing ([`ReportIssueTest.java`](../galvis/app/src/test/java/com/galvis/tvhome/ReportIssueTest.java))**:
   - Verified URL parameter encoding and QR bit-matrix generation (all 40 Gradle test tasks passing).

---

## 4. Emulator Setup & Verification (`emulator-5554`)

- Installed `galvis/app/build/outputs/apk/debug/app-debug.apk`.
- Uninstalled legacy prototype `com.galvis.launcher.debug`.
- Disabled stock emulator launchers (`com.google.android.apps.tv.launcherx` and `com.google.android.tvlauncher`) and locked `com.galvis.tvhome/.MainActivity` as the default Home activity (`isDefault=true`).
- Verified the "Report an Issue" modal live on the emulator (screenshot saved at [`docs/report_dialog.png`](report_dialog.png)).

---

## 5. Next Session Checklist

- [ ] **Push `v1.1.0` to Physical TCL 55C635 TV**:
  - Connect over Wi-Fi ADB (`adb connect 192.168.31.124:5555`) and install the updated APK.
  - Verify QR code scannability from couch viewing distance on the 55-inch 4K panel.
- [ ] **Customize GitHub Repository Target**:
  - Update `GITHUB_REPO_ISSUES_URL` in [`ReportIssueDialog.java`](../galvis/app/src/main/java/com/galvis/tvhome/ReportIssueDialog.java) to point to the final public GitHub repository URL before distributing to external testers.
- [ ] **Prepare Closed Beta Release Bundle**:
  - Build signed release APK / AAB (`./gradlew assembleRelease bundleRelease`) for the 20-tester Google Play Closed Testing pool.
