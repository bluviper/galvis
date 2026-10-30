# Privacy Policy for GALVis™ TV Launcher

**Effective Date:** October 30, 2026  
**Application:** GALVis™ TV Launcher (`com.galvis.tvhome`)  
**Developer:** Bluviper (`galvis.launcher@gmail.com`)  

---

### 1. Overview & Commitment
GALVis™ TV Launcher ("GALVis", "we", "our") is committed to respecting your privacy. We believe that your television is a private home appliance, not an advertising billboard or a data-harvesting vehicle.

GALVis is a **zero-telemetry, ad-free, 100% open-source application** licensed under the GNU General Public License v3.0 (GPLv3).

---

### 2. Information We Do NOT Collect
- **No Personal Information**: We do not collect names, email addresses, phone numbers, or account credentials.
- **No Browsing or Viewing History**: We do not track what applications you open, what media you watch, or what channels you tune into.
- **No Advertising IDs or Tracking**: We do not integrate Google AdMob, Firebase Analytics, Facebook SDK, or any commercial tracking SDKs.
- **No Background Telemetry**: The launcher does not send periodic background pings, analytics, or usage metrics to any remote server.

---

### 3. Permissions & Local Processing
GALVis requests minimal Android permissions solely to perform its essential functions locally on your television:
- **`ACCESS_NETWORK_STATE` & `ACCESS_WIFI_STATE`**: Used strictly on-device to display your current Wi-Fi SSID and connectivity status in the frosted network pill.
- **`ACCESS_FINE_LOCATION`**: Required by Android 10+ system APIs to read the local Wi-Fi network SSID. No geographical coordinates are ever recorded or transmitted.
- **`ACCESS_TV_SOURCE`**: Used exclusively on compatible smart TVs to enumerate and switch between hardware inputs (HDMI 1, HDMI 2 / eARC, HDMI 3, AV, and Live TV).
- **Package Visibility (`QUERY_ALL_PACKAGES` / `<queries>`)**: Used on-device to discover installed Leanback applications and render them on your home screen.

---

### 4. Optional User-Initiated Diagnostics (Beta Feedback)
When you choose to report a bug via **Settings > Report an Issue**:
- The application generates a QR code on your TV screen that opens a pre-filled issue URL on GitHub (`https://github.com/bluviper/galvis/issues/new`).
- This includes non-personal hardware telemetry: TV Model, Android OS version, display resolution, RAM usage, and build fingerprint.
- **This process is 100% user-initiated**. No data leaves your television until you scan the QR code and voluntarily click "Submit new issue" on GitHub using your own web browser.

---

### 5. Third-Party Services
GALVis does not use third-party cloud services or analytics vendors. External links (such as GitHub, Buy Me a Coffee, or Google Play) open in your device's web browser and are governed by their respective privacy policies.

---

### 6. Children's Privacy
GALVis does not knowingly collect or solicit any personal information from children or any users.

---

### 7. Changes to This Policy
Any updates to this Privacy Policy will be reflected in the public GitHub repository.

---

### 8. Contact Us
If you have any questions or feedback regarding this Privacy Policy, please contact us at:  
**Email:** `galvis.launcher@gmail.com`  
**GitHub:** [https://github.com/bluviper/galvis](https://github.com/bluviper/galvis)
