# GALVis™ TV Launcher v1.1.0 (Public Beta)

> **"A TV launcher should be fast, quiet, beautiful, and completely out of your way."**

We are thrilled to introduce the first public beta of **GALVis™ TV Launcher** (`v1.1.0-beta`). Built from scratch for low-to-mid memory TVs (such as the TCL 55C635 and Chromecast with Google TV), GALVis strips away all sponsored rows, auto-playing video trailers, and vendor bloat while preserving smooth 60 fps remote navigation.

---

### 🌟 Key Highlights & What's New

* **🍎 Apple tvOS-Inspired Glass Aesthetic**:
  * Frosted translucent dock band with hardware-accelerated ambient drop shadows.
  * **Dynamic App Halo Glow**: Extracts the dominant brand color of the focused app and casts a vibrant, atmospheric glow.
  * **140ms D-Pad Spring Physics**: Responsive, tactile navigation tailored for 10-foot couch control.
* **🔁 Continuous Horizontal Shelf Rewind**:
  * Pressing `DPAD_RIGHT` on the last card smoothly glides focus and the floating glass highlight back to Card 0 in ~350ms with zero cursor escaping.
* **📱 On-Screen QR Bug Reporting (`ReportIssueDialog`)**:
  * Built-in diagnostic tool accessible from *Settings > Report an Issue*. Point your phone camera at the screen to file a GitHub issue pre-filled with your TV's exact RAM, OS, build fingerprint, and display specs in 5 seconds—**no remote typing required**.
* **🔌 Frosted Hardware Inputs & Connectivity Flydowns**:
  * One-click switching for HDMI 1, HDMI 2 / eARC, HDMI 3, AV, and Live TV.
  * Frosted Wi-Fi & LAN status pill with connection diagnostics.
* **🖼️ 5 Calibrated 1080p Photographic Landscapes Bundled**:
  * Features *Pine Sprout*, *Fairy Glen*, *Modern Living*, *Oregon Coast*, and *Alpine Lake*. Rotates automatically or cycles instantly via remote without eating mobile data.
* **🛡️ Hardware-Safe & Debloat-Friendly**:
  * Retains **Widevine L1 4K HDR** streaming (Netflix, Prime Video, Disney+) and **Google Cast** without breakages.
  * Featherweight footprint: **<3 MB APK**, idling at **~25–30 MB PSS RAM** (saving 90%+ RAM for video rendering).

---

### 📦 Sideload & Installation

#### Method 1: Via ADB (Recommended for testing)
```bash
adb install -r app-release.apk
```

#### Method 2: Sideloading Directly on TV
1. Copy `app-release.apk` to a USB flash drive, or use TV apps like **Send Files to TV** or **Downloader**.
2. Open the file on your TV to install.
3. Open GALVis from your current app drawer to test navigation and input switching.

#### (Optional) Set as Default Launcher
- **Safe / Non-destructive**: Use **Button Mapper** or **Launch on Boot** to direct your remote's Home button to GALVis.
- **Full Debloat via ADB**:
  ```bash
  adb shell cmd package set-home-activity com.galvis.tvhome/.MainActivity
  ```

---

### 🔒 Package & Integrity Verification

| Parameter | Value |
|---|---|
| **Package ID** | `com.galvis.tvhome` |
| **Version Name** | `1.1.0` |
| **Version Code** | `2` |
| **Target SDK** | Android 14 (API 34) |
| **Min SDK** | Android 8.0 (API 26) |
| **File Name** | `app-release.apk` |
| **File Size** | `3,125,089 bytes` (~2.98 MB) |
| **SHA-256** | `9874C04F3D41052974ECA40E652E87BCF5B00EE6BA85157292B8B2F3A06ED0B7` |

---

### ☕ Support & Community

GALVis is **100% Free & Open Source** under the **GPLv3** license. No ads, no tracking, and no paywalls.

* 🐛 **Report a Bug**: [GitHub Issues](https://github.com/bluviper/galvis/issues) or scan the on-screen QR code on your TV.
* ✉️ **Support Inquiries**: `galvis.launcher@gmail.com`
* ☕ **Support the Project**: [Buy Me a Coffee](https://buymeacoffee.com/bluviper)
