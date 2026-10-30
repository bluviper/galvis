# GALVis™ TV Launcher — Community Launch & Onboarding Kit

**Project Status**: Public Beta (`v1.1.0`)  
**Package**: `com.galvis.tvhome`  
**License**: GPLv3 (100% Free & Open Source)  

---

## 1. The Story: How Galvis Was Born & Why It Was Conceived

### The Problem
Modern smart TVs and streaming sticks (Google TV, Android TV, Fire TV, PatchWall) have ceased being personal home appliances and have devolved into **ad-delivery billboards**. 
On low-to-mid-tier hardware (like budget 2 GB RAM 4K panels such as the TCL 55C635, Chromecast with Google TV HD/4K, and Xiaomi TVs):
- Over **50–65% of screen real estate** is monopolized by unskippable video trailers, sponsored banners, and algorithmic feeds.
- The stock launcher consumes **120 MB to 200+ MB of RAM** just idling in the background.
- Navigating with a standard IR/Bluetooth remote stutters at **15–30 frames per second**, accompanied by noticeable D-pad input latency.
- Heavy streaming services (Netflix 4K HDR, Prime Video, Disney+) crash or stutter due to the Low Memory Killer (LMK) terminating background TV services.

### The Alternatives and Their Shortcomings
1. **Commercial Custom Launchers (e.g., Projectivy)**: While capable, key functionality (custom wallpapers, multiple categories, advanced customization) is locked behind paid in-app upgrades or proprietary licensing.
2. **Companion Debloaters**: Phone-dependent apps that require tethered local ADB or complex command-line scripts risk breaking Widevine L1 DRM, disabling Google Cast, or breaking hardware HDMI switching.
3. **Legacy TV Launchers**: Many look outdated, rely on mobile touch paradigms with stretched phone icons, or lack proper 10-foot remote physics.

### The Conception of GALVis
**GALVis™** was conceived on a simple premise:
> *"A TV launcher should be fast, quiet, beautiful, and completely out of your way."*

It is engineered from scratch for the 10-foot television experience:
- **Zero Ads, Zero Telemetry, Zero Bloat**: Absolutely no trackers, analytics, or sponsored rows.
- **Microscopic Footprint**: Sub-500 KB core APK; runs under 30 MB runtime PSS—freeing up over 90% of system memory for 4K video decoding.
- **Apple TV-Inspired Glass Aesthetics**: Dynamic app halo glow extracted from app icons, frosted translucency, and continuous horizontal carousel rewind loops.
- **Hardware-First Compatibility**: Preserves Widevine L1 4K HDR playback, Google Cast, and TV input switching (HDMI 1/2/eARC, AV, Tuner).
- **Built-in Frictionless Telemetry**: An on-screen QR code dialog that lets beta testers report hardware bugs from their phone camera in under 5 seconds with zero remote typing.

---

## 2. Beta vs. Alpha: What Stage Is Galvis In?

**GALVis is firmly in Open / Public Beta (`v1.1.0`)**, not Alpha.

| Dimension | Alpha | Beta (GALVis Current State) |
|---|---|---|
| **Feature Completeness** | Missing core systems, fluctuating architecture. | **Complete**: Dock, horizontal shelves, inputs flydown, Wi-Fi switcher, wallpaper carousel, settings. |
| **Stability** | Frequent crashes, high risk of lockup. | **48-Hour Soak Tested**: Verified on physical hardware (TCL 55C635) under Netflix 4K and Google Cast. |
| **Goal** | Make it work. | **Device Compatibility**: Finding OEM quirks (Sony, Philips, Xiaomi, Nvidia, Onn, Chromecast) across Android 8 through 14. |
| **User Expectation** | Developer experimentation only. | **Daily Driver Candidate**: Safe for family living room use with a defined rollback path. |

---

## 3. Recommended XDA Subforum Selection

To maximize visibility and attract high-quality beta feedback, target these exact XDA subforums:

### Primary Thread (The Canonical Home)
- **Forum**: [XDA Android Development and Hacking > Android Apps and Games](https://forum.xda-developers.com/f/android-apps-and-games.393/)
- **Thread Title Format**:  
  `[APP][8.0+][OPEN SOURCE][BETA] GALVis™ TV Launcher — Ultra-light, ad-free glass launcher for Android TV & Google TV`

### Secondary / Regional Announcement Threads (Cross-linking back to Primary)
Post an informative announcement or compatibility note referencing the primary thread in device-specific subforums where users actively seek debloat solutions:
1. **Google Chromecast with Google TV**: [Google Chromecast with Google TV Forum](https://forum.xda-developers.com/c/chromecast-with-google-tv.11545/)
2. **NVIDIA Shield Android TV**: [NVIDIA Shield TV Forum](https://forum.xda-developers.com/c/nvidia-shield-android-tv.4735/)
3. **Xiaomi Mi Box / TV**: [Xiaomi Mi Box Forum](https://forum.xda-developers.com/c/xiaomi-mi-box.6033/)

*(Note: Follow XDA forum rules by maintaining one central development thread in "Android Apps and Games" and sharing brief pointers or answering user queries in device hubs).*

---

## 4. Ready-to-Post XDA Forum Pitch (Copy & Paste)

```markdown
[CENTER][B][SIZE=6]GALVis™ TV Launcher[/SIZE][/B]
[SIZE=4]Ultra-Lightweight • Zero Ads • Apple tvOS Glass Aesthetic • Built for Performance[/SIZE]

[IMG]https://raw.githubusercontent.com/galvis-project/galvis/main/docs/images/home_screen.png[/IMG]

[URL='https://github.com/bluviper/galvis'][B][COLOR=RoyalBlue]GitHub Repository[/COLOR][/B][/URL] | [URL='https://github.com/bluviper/galvis/releases'][B][COLOR=Green]Download Beta APK (v1.1.0)[/COLOR][/B][/URL] | [URL='https://buymeacoffee.com/bluviper'][B][COLOR=Orange]Buy Me A Coffee[/COLOR][/B][/URL]
[/CENTER]

Hey XDA Community,

If you own an Android TV or Google TV (especially budget/mid-range 2 GB RAM devices like TCL, Xiaomi, Hisense, or Chromecast with Google TV), you probably know the pain: **your $500+ screen has turned into an advertising billboard**.

Trailers auto-play, rows of "recommendations" clutter your home screen, and navigating with a remote feels like dragging a boulder through mud. Worse, stock launchers hog 150–200 MB of RAM, triggering low-memory stutter when you launch 4K Netflix or Prime Video.

Tired of closed-source alternatives with locked paywalls, I built **GALVis™** — an ultra-minimalist, high-performance, 100% Free & Open Source (GPLv3) launcher designed to liberate your TV.

---

[B][SIZE=5]✦ Key Features[/SIZE][/B]

• [B]Zero Ads & Pure Privacy[/B]: No banner ads, no tracking, no telemetry, no sponsored rows. What you install is what you see.
• [B]Featherweight Memory & Size[/B]: Core APK is under 500 KB (<3 MB with bundled 1080p photographic landscapes). Idles at ~25–30 MB PSS RAM, leaving 90%+ system memory free for video playback.
• [B]tvOS-Inspired Frosted Glass[/B]: Translucent dock overlays, hardware-accelerated ambient drop shadows, and 140ms D-pad spring physics.
• [B]Dynamic App Halo Glow[/B]: Intelligently extracts the active app's dominant brand color and casts a subtle atmospheric glow behind the focused tile.
• [B]Continuous Shelf Rewind Loop[/B]: Pressing Right on the last card in a shelf glides focus smoothly back to Card 0 in ~350ms, with zero cursor escaping.
• [B]Instant Inputs & Connectivity Flydowns[/B]: Switch HDMI ports (eARC/Live TV) and toggle Wi-Fi/LAN directly from frosted glass dropdowns without digging into nested system menus.
• [B]Hardware Tested & Safe[/B]: Preserves Google Cast and Widevine L1 4K HDR DRM handshakes.
• [B]One-Scan QR Bug Reporting[/B]: Hit a bug? Open the built-in Report dialog, scan the TV screen with your phone camera, and file an issue pre-filled with your TV's exact specs in 5 seconds.

---

[B][SIZE=5]✦ Screenshots[/SIZE][/B]

[CENTER]
[IMG]https://raw.githubusercontent.com/galvis-project/galvis/main/docs/images/home_screen.png[/IMG]
[I]Home Screen & 6-Column Dock with Frosted Glass[/I]

[IMG]https://raw.githubusercontent.com/galvis-project/galvis/main/docs/images/inputs_flydown.png[/IMG]
[I]Frosted Hardware Inputs Flydown Menu[/I]

[IMG]https://raw.githubusercontent.com/galvis-project/galvis/docs/report_dialog.png[/IMG]
[I]On-Device Beta Tester QR Telemetry Dialog[/I]
[/CENTER]

---

[B][SIZE=5]✦ Technical Specs[/SIZE][/B]

• [B]Minimum SDK[/B]: Android 8.0 (API 26)
• [B]Target SDK[/B]: Android 14 (API 34)
• [B]Architecture[/B]: Pure Java + AndroidX RecyclerView + DynamicAnimation spring physics
• [B]Soak Benchmark[/B]: 48-hour continuous soak passed on TCL 55C635 (Android 11 Google TV, 2 GB RAM)
• [B]License[/B]: GNU General Public License v3.0 (GPLv3)

---

[B][SIZE=5]✦ Getting Started (Beta Setup)[/SIZE][/B]

[B]Step 1: Install the APK[/B]
Download [B]galvis-v1.1.0-beta.apk[/B] from the GitHub Releases link above. Sideload it using any standard TV file transfer app (*Send Files to TV*, *Downloader*, USB flash drive, or ADB):
[CODE]adb install -r galvis-v1.1.0-beta.apk[/CODE]

[B]Step 2: Launch and Test[/B]
Open GALVis from your current app drawer. Verify that remote navigation, your installed apps, and input switching work smoothly.

[B]Step 3: Set as Default Launcher[/B]
• [I]Non-destructive testing (Recommended for Beta)[/I]: Use an app like [I]Button Mapper[/I] or [I]Launch on Boot[/I] to remap your remote's Home button to GALVis.
• [I]Full Debloat (Advanced/ADB)[/I]: Disable the stock Google TV launcher via ADB:
[CODE]adb shell cmd package set-home-activity com.galvis.tvhome/.MainActivity[/CODE]
(To revert at any time, see the rollback guide below).

---

[B][SIZE=5]✦ Safe Rollback Guide[/SIZE][/B]
Never worry about bricking your TV. To return to the factory launcher at any time:
[CODE]
# Re-enable stock Google TV launcher
adb shell pm enable com.google.android.apps.tv.launcherx
# Or uninstall GALVis
adb uninstall com.galvis.tvhome
[/CODE]

---

[B][SIZE=5]✦ How You Can Help (Beta Feedback)[/SIZE][/B]
We need real-world feedback from different TV brands (Sony Bravia, Philips, Hisense, Xiaomi, Onn, Nvidia Shield, Fire TV).
If you encounter any layout anomalies, focus jumps, or missing HDMI inputs:
1. Open [B]Settings[/B] in GALVis > Select [B]Report an Issue[/B].
2. Point your phone camera at the on-screen QR code.
3. Submit the pre-filled GitHub issue!

Alternatively, post your feedback and TV model right here in this XDA thread.

---

[B][SIZE=5]✦ Support the Project[/SIZE][/B]
GALVis is 100% free and open source. No features are locked behind paywalls, and there will never be ads. If GALVis brought speed and sanity back to your living room TV, buying a coffee is greatly appreciated!

☕ [URL='https://buymeacoffee.com/bluviper'][B]Buy Me A Coffee[/B][/URL]
✉️ Contact: [B]galvis.launcher@gmail.com[/B]
```

---

## 5. Support System Setup Blueprint

Setting up structured, low-friction support channels ensures tester feedback is actionable rather than noisy.

```
                    ┌───────────────────────────┐
                    │     Beta User on TV       │
                    └─────────────┬─────────────┘
                                  │
         ┌────────────────────────┴────────────────────────┐
         │ (Preferred: Zero Remote Typing)                 │ (Alternative)
         ▼                                                 ▼
┌──────────────────────────────┐              ┌─────────────────────────────┐
│  On-Screen QR Code Dialog    │              │ XDA Thread / Feedback Form  │
│  (ReportIssueDialog.java)    │              └──────────────┬──────────────┘
└──────────────┬───────────────┘                             │
               │ Camera Scan                                 │
               ▼                                             ▼
┌──────────────────────────────┐              ┌─────────────────────────────┐
│  Pre-filled GitHub Issue URL │              │  Dedicated Support Email    │
│  with Live TV Telemetry      │              │  (feedback@galvis.app)      │
└──────────────────────────────┘              └─────────────────────────────┘
```

### Action Items for Launch

1. **GitHub Issues (Primary Channel)**:
   - Ensure the repository issue tracker is public.
   - Update `GITHUB_REPO_ISSUES_URL` in [`ReportIssueDialog.java`](file:///D:/ai-playgrnd/android-tv/galvis/app/src/main/java/com/galvis/tvhome/ReportIssueDialog.java#L42) to point to your real repository (e.g. `https://github.com/YOUR_USER/galvis/issues/new`).
   - Add two Issue Templates (`.github/ISSUE_TEMPLATE/`):
     - `bug_report.md` (Matches the telemetry fields: Manufacturer, Model, OS, DPI, RAM).
     - `device_compatibility.md` ("I tested Galvis on [TV Model] — Here's what works").

2. **Dedicated Support Email**:
   - Create a clean forwarding alias or inbox: e.g., `galvis.launcher@gmail.com` or `support@galvis.app`.
   - Useful for users who don't have GitHub accounts or need private assistance.

3. **Google Form for General Non-GitHub Testers (Optional 2-Minute Triage)**:
   - Question 1: TV Model & Android OS Version.
   - Question 2: Did all your apps & inputs appear correctly? (Yes / No).
   - Question 3: Any bugs, remote lag, or suggestions?
   - Question 4: Would you like to join the closed Google Play Store testing track? (Email field).

4. **Google Play 20-Tester Closed Testing Pool**:
   - Create Google Group: `galvis-testers@googlegroups.com`.
   - When submitting to the Play Console, link this Google Group to meet the mandatory 20-tester / 14-day rule.

---

## 6. Monetization & Buy Me a Coffee Etiquette

### Should You Include the Buy Me a Coffee Link Now?
**Yes, absolutely.** You already created the link, and there is zero reason to hide it—provided it follows the **XDA Open Source Community Etiquette**:

1. **Positioning**:
   - Never put donation links in the headline, first paragraph, or download section.
   - Keep it at the very bottom under a humble "Support the Project / Coffee Fund" heading.
2. **Value Alignment**:
   - XDA members are notoriously allergic to "freemium traps" and commercial spam masquerading as open-source projects.
   - Explicitly highlight: **"Zero paywalls, zero ads, zero telemetry, GPLv3 licensed."**
   - When users realize they get a production-grade, ad-free launcher for free without being nagged to upgrade to "Pro", they are far more likely to tip voluntarily.
3. **Transparency**:
   - State that contributions help cover test hardware costs, Google Play Developer account fees, or just late-night coffee.
