# Privacy Policy for GALVis TV Home

**Last Updated:** October 24, 2026

GALVis TV Home (`com.galvis.tvhome`) is built from the ground up to be a private, offline, bloatware-free TV launcher for Android TV and Google TV devices.

### 1. Data Collection & Telemetry
**GALVis TV Home does not collect, track, log, or transmit any personal data or usage analytics.**
- No telemetry libraries (Firebase, Google Analytics, App Center, Sentry, etc.) are included in the codebase.
- No network requests are made by the launcher core. The launcher does not communicate with external servers, cloud providers, or third-party marketing services.

### 2. Permissions & Local Device Access
GALVis TV Home only requests permissions strictly required to perform its function as an on-device TV launcher:
- **`android.permission.QUERY_ALL_PACKAGES`**: Required to enumerate installed applications and TV inputs so they can be displayed on your TV Home screen. This list never leaves your device.
- **`android.permission.ACCESS_FINE_LOCATION` / `ACCESS_COARSE_LOCATION`**: Optional, used solely to compute local sunrise and sunset times on-device to switch between day and night wallpaper tinting. Location coordinates are processed exclusively in RAM and are never recorded or transmitted.
- **`android.permission.READ_EXTERNAL_STORAGE` / Media Permissions**: Used strictly if you choose to load custom wallpapers or caption files from local TV storage or USB drives.

### 3. Data Storage
All user configuration (docked apps, custom wallpapers, rotation timer, and UI toggles) is stored locally on the device using standard Android `SharedPreferences` in private internal storage. No data is synchronized to the cloud or shared with third parties.

### 4. Children's Privacy
GALVis TV Home does not collect any information from any user, including children under the age of 13.

### 5. Open Source Transparency
The source code of GALVis TV Home is open-source. Anyone can inspect and verify that no tracking or data extraction logic is present.

### 6. Contact
If you have questions or concerns regarding this privacy policy, please open an issue in the project repository.
