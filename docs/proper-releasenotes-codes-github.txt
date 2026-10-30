# GALVis™ TV Launcher v1.1.0 (Public Beta)

> **"A TV launcher should be fast, quiet, beautiful, and completely out of your way."**

We're thrilled to introduce the first public beta of **GALVis™ TV Launcher** (`v1.1.0-beta`). Built from scratch for low-to-mid memory TVs (such as the TCL 55C635 and Chromecast with Google TV), GALVis strips away sponsored rows, auto-playing video trailers, and vendor bloat while keeping remote navigation smooth at 60 fps.

---

## 🌟 Key Highlights

### 🍎 tvOS-Inspired Glass Aesthetic
- Frosted translucent dock band with hardware-accelerated ambient drop shadows
- **Dynamic App Halo Glow:** extracts the dominant brand color of the focused app and casts a vibrant glow behind it
- **140 ms D-Pad spring animation:** responsive, tactile navigation tuned for 10-foot couch control

### 🔁 Continuous Shelf Rewind
- Pressing `DPAD_RIGHT` on the last card glides focus and the floating glass highlight back to the first card in ~350 ms, with no focus escaping the shelf

### 📱 On-Screen QR Bug Reporting
- Go to **Settings > Report an Issue** and point your phone camera at the screen
- Opens a GitHub issue pre-filled with your TV's RAM, OS, build fingerprint, and display specs, with **no remote typing required**

### 🔌 Hardware Inputs & Connectivity
- One-click switching for HDMI 1, HDMI 2 / eARC, HDMI 3, AV, and Live TV
- Frosted Wi-Fi and LAN status pill with connection diagnostics

### 🖼️ 5 Bundled 1080p Landscapes
- *Pine Sprout*, *Fairy Glen*, *Modern Living*, *Oregon Coast*, and *Alpine Lake*
- Rotates automatically or cycles instantly via remote, with no downloads needed

### 🛡️ Hardware-Safe & Lightweight
- Retains **Widevine L1 4K HDR** streaming (Netflix, Prime Video, Disney+) and **Google Cast**
- **<3 MB APK**, idling at **~25–30 MB PSS RAM**, leaving the rest free for video playback

---

## 📦 Sideload & Installation

### Method 1: ADB (recommended for testing)

```bash
adb install -r app-release.apk
```

### Verify your download

```bash
# Linux / macOS
sha256sum app-release.apk

# Windows
certutil -hashfile app-release.apk SHA256
```

---

### Method 2: Without a computer (Downloader app)

1. On your TV, install **Downloader** (by AFTVnews) from the Play Store.
2. Go to **Settings > Device Preferences > Security & restrictions > Install unknown apps** (the path varies by TV) and allow **Downloader**.
3. Open Downloader and enter the direct link to `app-release.apk` from this release's **Assets** section below.
4. When the download finishes, choose **Install**.

### Method 3: USB drive

1. Download `app-release.apk` on your computer and copy it to a USB drive.
2. Plug the drive into your TV and open it with a file manager app.
3. Select the APK and choose **Install** (you may need to allow unknown apps for the file manager first).

> After installing, press Home and select **GALVis TV Launcher** as your default home app if prompted.

---

## ⚠️ Known Issues

- [Issue 1, e.g. behavior on a specific TV or Android version]
- [Issue 2]
- This is a **beta**. Please report problems via **Settings > Report an Issue** or the Issues tab.

> **Privacy note:** The QR bug report pre-fills your TV's RAM, OS version, build fingerprint, and display specs into a public GitHub issue. Review the issue text before submitting.

## 🔧 Build Info

| Parameter | Value |
|---|---|
| Package ID | `com.galvis.tvhome` |
| Version Name | 1.1.0 |
| Version Code | 2 |
| Target SDK | Android 14 (API 34) |
| Min SDK | Android 8.0 (API 26) |
| File Name | `app-release.apk` |
| File Size | 3,125,089 bytes (~2.98 MB) |
| SHA-256 | `9874C04F3D41052974ECA40E652E87BCF5B00EE6BA85157292B8B2F3A06ED0B7` |
  ### ☕ Support & Community

  GALVis is 100% Free & Open Source under the GPLv3 license. No ads, no tracking, and no paywalls.

  • 🐛 Report a Bug: GitHub Issues https://github.com/bluviper/galvis/issues or scan the on-screen QR code on your TV.
  • ✉️ Support Inquiries: galvis.launcher@gmail.com
  • ☕ Support the Project: Buy Me a Coffee https://buymeacoffee.com/bluviper