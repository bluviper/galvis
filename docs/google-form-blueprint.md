# GALVis™ TV Launcher — Beta Tester Compatibility Form Blueprint

**Purpose**: Frictionless 2-minute triage form for non-GitHub users (Reddit, XDA Developers, AVForums, Discord) to report hardware compatibility and opt into the Google Play Closed Beta testing track.

---

## 1. Form Specifications

| Parameter | Specification |
|:---|:---|
| **Form Title** | `GALVis™ TV Launcher — Beta Tester Compatibility Form` |
| **Description** | Help us test GALVis across as many TV brands as possible. Takes less than 2 minutes. |
| **Estimated Completion Time** | 90–120 seconds |
| **Target Distribution Channels** | Reddit (`r/AndroidTV`, `r/GoogleTV`, `r/ShieldAndroidTV`), XDA Developers, Telegram, Discord |

---

## 2. Field Definitions & Schema

### Field 1: Device Model & Manufacturer
- **Type**: Short Answer
- **Required**: Yes
- **Question Text**: `TV / Device Brand & Model`
- **Help Text / Description**: `e.g., Sony Bravia X90J, TCL 55C635, Chromecast with Google TV 4K, Nvidia Shield TV Pro, Xiaomi TV Box S`

---

### Field 2: Operating System Version
- **Type**: Multiple Choice (Radio Buttons)
- **Required**: Yes
- **Question Text**: `Android Version`
- **Options**:
  1. `Android 9 (Pie)`
  2. `Android 10 (Q)`
  3. `Android 11 (Red Velvet Cake)`
  4. `Android 12 (Snow Cone)`
  5. `Android 13 / 14`
  6. `Other` (with text entry enabled)

---

### Field 3: Navigation & Fluidity Check
- **Type**: Multiple Choice (Radio Buttons)
- **Required**: Yes
- **Question Text**: `Did remote D-pad navigation feel smooth and responsive?`
- **Options**:
  1. `Yes, buttery 60 fps`
  2. `Noticeable lag`
  3. `Focus got lost`

---

### Field 4: Inputs & Apps Visibility Check
- **Type**: Multiple Choice (Radio Buttons)
- **Required**: Yes
- **Question Text**: `Did your HDMI inputs and TV apps appear correctly?`
- **Options**:
  1. `Yes, all accounted for`
  2. `Some missing`
  3. `N/A (Streaming stick / Box without TV tuner or HDMI inputs)`

---

### Field 5: Open Feedback & Bug Reports
- **Type**: Paragraph (Long Answer)
- **Required**: No
- **Question Text**: `Bugs or Suggestions?`
- **Help Text / Description**: `Describe any visual glitches, crashes, audio drops, remote button quirks, or wishlist items.`

---

### Field 6: Google Play Closed Beta Opt-In
- **Type**: Short Answer
- **Required**: No
- **Question Text**: `(Optional) Gmail Address`
- **Help Text / Description**: `Enter if you'd like an invite to the official Google Play Store Closed Beta track (requires a Google account).`
- **Input Validation**:
  - Regular Expression Match: `^[a-zA-Z0-9._%+-]+@gmail\.com$`
  - Error Text: `Please enter a valid @gmail.com address.`

---

## 3. Recommended Form Settings

1. **Responses**:
   - **Send responders a copy of their response**: Off (or When requested).
   - **Allow response editing**: Enabled (allows testers to update their specs if they reflash or test another TV).
   - **Limit to 1 response**: Disabled (testers often own multiple devices, e.g., living room Sony TV + bedroom Chromecast).
2. **Presentation**:
   - **Show progress bar**: Disabled (single-page form).
   - **Confirmation message**:
     ```text
     Thank you for helping test GALVis™ TV Launcher! Your hardware telemetry helps us optimize performance across diverse TV chipsets.

     If you provided a Gmail address, you'll receive a Google Play Closed Testing invitation link shortly.

     GitHub Repository & Release Notes: https://github.com/galvis/galvis
     ```

---

## 4. Automated 1-Click Form Generator (Google Apps Script)

Instead of creating the form manually click-by-click, you can run this Google Apps Script to generate the exact form, pre-configured with descriptions, options, validations, and a linked Google Sheet in 5 seconds.

### Setup Instructions:
1. Open [script.google.com](https://script.google.com/) and click **New project**.
2. Replace all code in `Code.gs` with the snippet below.
3. Click **Run** (`createGalvisBetaForm`).
4. Review permissions when prompted by Google.
5. The Execution Log will output both the **Edit URL** and the **Public Sharing URL**.

```javascript
function createGalvisBetaForm() {
  const form = FormApp.create('GALVis™ TV Launcher — Beta Tester Compatibility Form');
  
  form.setDescription('Help us test GALVis across as many TV brands as possible. Takes less than 2 minutes.');
  form.setAllowResponseEdits(true);
  form.setLimitOneResponsePerUser(false);
  form.setConfirmationMessage(
    "Thank you for helping test GALVis™ TV Launcher! Your hardware telemetry helps us optimize performance across diverse TV chipsets.\n\n" +
    "If you provided a Gmail address, you'll receive a Google Play Closed Testing invitation link shortly.\n\n" +
    "GitHub Repository & Release Notes: https://github.com/galvis/galvis"
  );

  // 1. TV / Device Brand & Model
  const item1 = form.addTextItem();
  item1.setTitle('TV / Device Brand & Model')
       .setHelpText('e.g., Sony Bravia X90J, TCL 55C635, Chromecast with Google TV 4K, Nvidia Shield TV Pro, Xiaomi TV Box S')
       .setRequired(true);

  // 2. Android Version
  const item2 = form.addMultipleChoiceItem();
  item2.setTitle('Android Version')
       .setChoiceValues([
         'Android 9 (Pie)',
         'Android 10 (Q)',
         'Android 11 (Red Velvet Cake)',
         'Android 12 (Snow Cone)',
         'Android 13 / 14'
       ])
       .showOtherOption(true)
       .setRequired(true);

  // 3. Remote D-pad navigation responsiveness
  const item3 = form.addMultipleChoiceItem();
  item3.setTitle('Did remote D-pad navigation feel smooth and responsive?')
       .setChoiceValues([
         'Yes, buttery 60 fps',
         'Noticeable lag',
         'Focus got lost'
       ])
       .setRequired(true);

  // 4. HDMI inputs and TV apps appearance
  const item4 = form.addMultipleChoiceItem();
  item4.setTitle('Did your HDMI inputs and TV apps appear correctly?')
       .setChoiceValues([
         'Yes, all accounted for',
         'Some missing',
         'N/A (Streaming stick / Box without TV tuner or HDMI inputs)'
       ])
       .setRequired(true);

  // 5. Bugs or Suggestions?
  const item5 = form.addParagraphTextItem();
  item5.setTitle('Bugs or Suggestions?')
       .setHelpText('Describe any visual glitches, crashes, audio drops, remote button quirks, or wishlist items.')
       .setRequired(false);

  // 6. (Optional) Gmail Address
  const item6 = form.addTextItem();
  const emailValidation = FormApp.createTextValidation()
    .requireTextContains('@')
    .setHelpText('Please enter a valid email address if you wish to receive an invite.')
    .build();

  item6.setTitle('(Optional) Gmail Address')
       .setHelpText("Enter if you'd like an invite to the official Google Play Store Closed Beta track (requires a Google account).")
       .setValidation(emailValidation)
       .setRequired(false);

  Logger.log('Form created successfully!');
  Logger.log('Edit URL: ' + form.getEditUrl());
  Logger.log('Published Form URL: ' + form.getPublishedUrl());
}
```

---

## 5. Forum & Reddit Outreach Copy

When distributing the form on community forums or subreddits, use this ready-to-use template:

```markdown
**[Beta Call] Testing GALVis™ TV Launcher across TV brands (2-Minute Feedback)**

Hey everyone! We're beta testing **GALVis™**, an ultra-lightweight (sub-500 KB), zero-bloat standalone Android TV launcher with 60 fps spring physics and clean glass aesthetic.

No GitHub account required — if you have 2 minutes and an Android TV / Google TV device, we’d love your feedback on remote fluidity and compatibility:

👉 **Beta Tester Form**: [INSERT_GOOGLE_FORM_LINK_HERE]

**What we're looking for:**
- TV / Streaming box model (Sony, TCL, Philips, Nvidia Shield, Chromecast, etc.)
- Did D-pad navigation stay buttery smooth at 60 fps?
- Did your HDMI inputs and streaming apps load properly?

*(Optional)* Leave your Gmail in the form if you want an invite to the 20-tester Google Play Closed Beta track.

Thanks for helping us build an ad-free, snappy TV launcher for everyone!
```
