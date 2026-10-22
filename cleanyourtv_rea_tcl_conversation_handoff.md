# Conversation Transcript: CleanYourTV, REA, and a Custom TCL TV Launcher

**Purpose:** Share this conversation with other AI agents for continuity
and technical follow-up.\
**Date:** October 22, 2026\
**Target device:** TCL 55C635, Google TV\
**Primary goals:** Explore a custom launcher, reduce
tracking/promotional content, preserve Google Cast, and understand
options for YouTube ads.

> **Transcript note:** This document preserves the user's requests and
> the substantive assistant responses in chronological order. Web
> citations are retained as reference markers from the original chat;
> other agents should independently verify current repository contents
> and compatibility before recommending commands.

------------------------------------------------------------------------

## 1. User: CleanYourTV repository assessment

**User**

> tell me what i need to know for this repo
> https://github.com/Gathin23/cleanyourtv what can i do with it, the ads
> that i can and cant block, why debloat an android tv from TCL. can
> this be undone?

**Assistant**

# CleanYourTV: What it can do for your TCL Android TV

The short version: **CleanYourTV can replace the cluttered, ad-heavy TV
home screen with a clean, Apple TV-style launcher, disable selected
manufacturer apps and services, and optionally reduce ads and tracking
through Private DNS.** It does this without root access or permanently
uninstalling the packages it targets. (Original citations:
`turn792644search0`, `turn792644search1`.)

My recommendation is to consider it if you want more control over your
TCL TV, but don't run a generic debloat list blindly. The correct
procedure depends on your exact TCL model, firmware and Android TV or
Google TV version.

## What exactly is this repository?

**CleanYourTV** is an open-source project by Gathin23. It is a
collection of scripts, device-specific package lists and a small Android
launcher---not simply an ad-blocking app.

Its three main parts:

1.  **Replacement home screen:** An uncluttered interface with chosen
    apps, a configurable dock, themes, wallpaper, clock, settings and
    input shortcuts. It replaces the stock home experience rather than
    modifying the design of the original launcher.
2.  **Debloating tools:** Uses Android Debug Bridge (ADB) to disable
    selected packages, potentially including manufacturer promotions,
    analytics, sponsored content components and unwanted preinstalled
    applications.
3.  **Optional DNS-based ad and tracker blocking:** Can configure
    Android's Private DNS to use a filtering resolver such as AdGuard
    DNS. This is separate from the launcher and has different
    limitations.

These functions are distinct: installing the launcher alone does not
block every advertisement, and disabling system packages does not
automatically filter ads inside every streaming app.

## What ads can you block?

There are several different advertising mechanisms on a smart TV, and no
single technique reliably blocks all of them.

  -----------------------------------------------------------------------
  Advertising source      Expected result         How
  ----------------------- ----------------------- -----------------------
  Sponsored tiles and     Strong potential        Replace the stock
  recommendation rows on                          launcher and disable
  the TCL home screen                             relevant components
                                                  where safe

  Manufacturer            Partial to strong       Disable identified
  promotions, telemetry                           packages; DNS can block
  and tracking                                    some associated domains

  Ad banners inside apps  Partial                 Private DNS, if the ad
                                                  domains are separately
                                                  identifiable

  YouTube video           Generally no            Ads often share
  advertisements                                  delivery infrastructure
                                                  with video content

  Netflix, Prime Video or Unreliable              DNS may block some
  Disney+ ads on                                  requests, but can also
  ad-supported plans                              break playback or other
                                                  app functions

  Ads embedded into a     No reliable solution    These are not
  video stream or its                             necessarily separate
  content                                         network requests

  Ads shown by an         Not directly            The TV launcher cannot
  HDMI-connected device                           control the other
                                                  device's interface
  -----------------------------------------------------------------------

### The three mechanisms

**A. Home-screen advertising**

This is where CleanYourTV can make the biggest visual difference. If the
stock TCL launcher shows sponsored banners, promotional rows or content
recommendations, replacing that stock interface removes it from your
normal Home screen. It does not remove advertising within Netflix,
YouTube or other apps.

**B. DNS-based blocking**

The TV asks a DNS resolver to translate a domain name into an IP
address. A filtering resolver can refuse to resolve known advertising
and tracking domains. This works best when ads use separate,
identifiable domains. If the same infrastructure delivers both the video
and the advertisement, blocking the domain may also break the video.

**C. Ads integrated into streaming**

YouTube is the clearest example. Because advertisements can be served
through the same infrastructure as the videos, ordinary DNS filtering
cannot reliably distinguish the two. Don't expect CleanYourTV to give
you a universal, ad-free streaming experience.

**Conclusion:** If the primary annoyance is the TCL home screen pushing
content, the project addresses that. If the objective is eliminating
every ad from every streaming service, this repository alone will
disappoint.

## Why debloat a TCL Android TV?

Debloating can offer:

-   **A cleaner, faster-feeling interface:** Open apps directly instead
    of navigating sponsored rows. A lightweight launcher may reduce some
    home-screen overhead, especially on lower-memory TVs, but it won't
    magically make every app faster.
-   **Less tracking and promotional activity:** Disabling selected
    analytics and advertising components may reduce some data collection
    and background activity. It does not guarantee privacy; Google and
    streaming apps can still collect data.
-   **Control over the home screen:** Choose the dock, theme, wallpaper,
    layout and shortcuts.
-   **Less unwanted software:** Some preinstalled apps can be disabled.
    However, removing background components can break voice search,
    casting, input switching, TV tuner features, app recommendations or
    firmware updates if the wrong packages are selected.

Debloating is not automatically beneficial. A working TV with a slightly
annoying home screen may be better left alone than a customized TV with
broken updates or remote-control behavior.

### Components to preserve unless proven otherwise

-   Google Play Store and Google Play services.
-   Chromecast built-in / Google Cast, if casting from a phone.
-   Netflix and other streaming apps in use.
-   HDMI, Live TV, tuner and input-related services.
-   Remote-control, system settings, accessibility and voice-assistant
    components in use.
-   Firmware and security update services.

The repository documentation reportedly warns that some China-market TCL
variants may reject third-party launchers altogether. (Original
citations: `turn792644search1`, `turn389524search0`.)

## Can it be undone?

**Generally, yes.** The project disables Android packages rather than
uninstalling them, and it provides a restore script. But reversible does
not mean risk-free.

-   **Disabled packages:** The restore script re-enables packages listed
    in the debloat list, using the recorded pre-change state. Packages
    that were already disabled should remain disabled rather than being
    indiscriminately enabled.
-   **Original launcher:** The restore process can return Home to the
    stock launcher. The custom launcher remains installed until removed
    from Settings → Apps.
-   **What may not be restored automatically:** DNS changes, unrelated
    manual changes, and some app-specific settings may need separate
    attention. A restore script cannot guarantee recovery if the TV
    becomes unreachable or a critical system component is disabled.
-   **Firmware updates:** Some device lists disable update services.
    Restore those services before checking for updates; updates can
    alter package and launcher behavior.

### Safe recovery plan

1.  Save a copy of the exact debloat list and generated recovery record.
2.  Keep the computer on the same local network as the TV.
3.  Confirm the custom launcher opens, the remote works, streaming apps
    launch, and casting works before disabling anything else.
4.  Keep USB/network debugging enabled only as long as needed. Disable
    debugging after finishing and verifying the TV works.
5.  To revert, use the repository's restore script with the original
    list and correct TV IP. If ADB is unavailable, try Settings → Apps
    to re-enable relevant packages.

Don't rely on a factory reset as the first recovery method. It can erase
settings and accounts without necessarily solving every
firmware-specific issue.

## Installation overview

The repository supports Windows workflows, so Linux and rooting are not
required. It uses ADB over the local network and builds the launcher
from source.

1.  Identify the exact TV model and firmware in Settings → System or
    Device Preferences → About.
2.  Download the repository and inspect `README.md`, `PROMPT.md`,
    `docs/TROUBLESHOOTING.md` and relevant `examples/` files. Don't
    execute a script before understanding which packages it disables.
3.  Enable developer options and debugging. On many models, press OK on
    Build several times in About, then enable USB debugging and, if
    offered, network debugging.
4.  Use device-probe tools to identify the TV and inspect packages.
    Confirm that an example list matches the actual hardware and
    firmware.
5.  Back up, install the launcher and test it before switching away from
    stock. Keep a recovery record.
6.  Test remote, Home button, HDMI inputs, casting, voice search and
    streaming apps. Treat DNS filtering as a separate optional change.

**Security note:** USB debugging gives an authorized computer
substantial control over the TV. Only authorize a trusted computer,
never expose ADB to the internet, and turn debugging off after use.

## Initial recommendation and model question

The recommendation was to start with the launcher and minimum necessary
debloating, leave firmware updates enabled, and add DNS filtering only
if desired.

The assistant asked for the exact model, OS, primary goal and whether
Google Cast needed to be preserved.

------------------------------------------------------------------------

## 2. User: Exact TV details

**User**

> Assess CleanYourTV compatibility and safest plan for my TCL TV. Model:
> 55C635. OS: Google TV. Goal: Both plus a cleaner interface. Preserve
> Chromecast/Google Cast: yes. Check the repository's matching examples,
> compatibility caveats, and rollback path before recommending commands.

**Assistant**

# CleanYourTV assessment for your TCL 55C635

**Verdict: proceed with inspection, but don't apply another TCL model's
debloat list.**

The TCL 55C635 is a 55-inch C635 QLED 4K television. The stated
goal---cleaner home screen, less promotional content and reduced
tracking while retaining Google Cast---is achievable in principle.

The repository and its device examples were reviewed, but a tested
package list specifically for the **TCL 55C635** could not be verified.
Similar-looking TCL examples are not sufficient evidence of
compatibility. (Original citations: `turn223701search0`,
`turn223701search1`, `turn223701search3`.)

### Compatibility assessment

  -----------------------------------------------------------------------
  Feature                             Assessment
  ----------------------------------- -----------------------------------
  Install a custom launcher           Likely feasible, subject to
                                      firmware

  Replace sponsored home screen       Likely feasible if third-party Home
                                      works

  Disable selected advertising and    Possible, but package
  analytics packages                  identification is needed

  Preserve Google Cast                Achievable if Cast-related
                                      components remain enabled and are
                                      tested

  Use a pre-tested 55C635 debloat     Not verified
  list                                

  Roll back changes                   Supported by project, with recovery
                                      precautions
  -----------------------------------------------------------------------

The C635 product name alone isn't enough to choose packages. Regional
firmware, Android version, launcher package names and vendor
customizations can differ even within a product family.

The repository documentation also warns that some Google TV setups can
behave badly when the stock launcher is disabled. Its installer has been
updated to install and start the replacement launcher first, disable the
original launcher afterward, and check the resulting Home behavior.
(Original citations: `turn223701search0`, `turn223701search4`.)

### Closest examples---not safe to copy blindly

The repository release was described as **v2.5.0**, dated October 21,
2026, and examples covered many TV models. (Original citations:
`turn296702view3`, `turn296702view1`.)

-   **TCL 43C645:** Reference only. Its example covers TCL promotions,
    analytics, Google components and launcher setup, but reportedly uses
    a Realtek platform and Android TV 11---not a confirmed 55C635
    firmware match. (Original citation: `turn437415view1`.)
-   **TCL 65C735:** Reference only. This Google TV example preserves
    Chromecast and Miracast, but its G05 platform and firmware differ
    from the 55C635. Its package list should not be copied. (Original
    citation: `turn437415view1`.)
-   **Chromecast with Google TV 4K:** The repository notes that its
    setup wizard provides Cast-related credentials on that device. This
    is a reason to preserve setup and Cast services until their roles
    are established, but it does not prove the TCL uses the same
    implementation. (Original citation: `turn437415view1`.)

### Recommended phased plan

1.  **Read-only inspection:** Record exact Android version, firmware
    build, device identifiers, launcher package, installed packages and
    current Cast-related services. Do not disable packages.
2.  **Launcher only:** Build and install CleanYourTV's launcher using
    the current Windows PowerShell workflow. Verify it starts and the
    remote's Home button behaves correctly. Keep the stock launcher and
    system services available during testing.
3.  **Minimal debloating:** Create a custom list from the actual package
    inventory. Start with clearly identified promotional and analytics
    components, a small group at a time. Preserve Google Cast, firmware
    updates, HDMI, Live TV, remote control, Bluetooth, Google Play
    services and uncertain dependencies.
4.  **Optional DNS filtering:** After the TV is stable, test Private DNS
    if desired. Record the original DNS setting and revert if apps,
    casting or playback break.

### Read-only Windows preparation

The repository includes PowerShell scripts. The suggested starting point
was to follow its Windows setup instructions and use the latest release,
not an unreviewed repackaging. (Original citations: `turn437415view2`,
`turn437415view3`.)

After cloning the repository and setting up its Python environment, the
first TV command should be a **probe**, not a debloat command:

``` powershell
# Replace this with your TCL's LAN IP address.
$env:TV_HOST = "192.168.1.50"

# Read the TV model, Android version and firmware.
tools\venv\Scripts\python.exe -I tools\tv.py --probe
```

The IP is an example only. Use the address shown in the TV's network
settings. First ensure ADB is enabled and the TV has authorized the
computer; some newer firmware requires a wireless-debugging pairing
step.

This command is intended to inspect the device, not apply a debloat
list. **Do not run `disable.ps1` yet.**

### Rollback plan

The repository provides `tools/restore.sh` and a Windows PowerShell
counterpart. Its recovery workflow uses the list applied and records
package states. For Google TV, the stock launcher package may also need
to be specified. (Original citations: `turn437415view2`,
`turn437415view3`.)

Before changing anything:

-   Save the original package inventory and firmware details.
-   Keep the exact custom `debloat.txt` file used.
-   Keep the TV reachable over the trusted network.
-   Know how to access Settings → Apps with the physical remote.
-   Keep Google Cast and the setup wizard untouched until dependencies
    are confirmed.

Restoring disabled packages is not the same as undoing every change. DNS
settings, launcher configuration and packages disabled manually outside
the recorded list may need separate restoration.

The assistant requested the read-only probe output or a photo of
Settings → System → About, including software version and build number,
to make a model-specific decision.

------------------------------------------------------------------------

## 3. User: Not ready to install; asks about direct access and reverse engineering

**User**

> ok, i do not have the soonest plan to do that. but fancy me, if you
> have the access to the tv itself, can you use githubs REA tool to
> reverse engineer the launcher, and install it on the tv?

**Assistant**

Yes---in principle, I could help reverse-engineer, customize, build and
install CleanYourTV on the TCL 55C635 if there were an authorized
connection to the TV and a computer environment with the required tools.
I do not currently have direct access to the TV or its local network
from this chat, so I cannot perform the installation remotely right now.

The distinction: CleanYourTV does not need to reverse-engineer the
original launcher internally. It is a native Java Android TV launcher
(`com.gathin.tvhome`) that replaces the home screen, while its tools
inspect the TV's installed packages and settings over ADB. (Original
citations: `turn710157search1`, `turn710157search0`.)

### What could be done with access

1.  **Inspect and reverse-engineer the environment:** Identify firmware,
    launcher, package inventory, Home-button behavior, available
    debugging interfaces and Cast-related components. Capture
    screenshots before changing anything.
2.  **Customize and build the launcher:** Start from the repository's
    Java source, adjust configuration and theme, build an APK with the
    required Android SDK, and verify the build before installation.
3.  **Install conservatively:** Install the new launcher first, test it,
    and only then consider disabling the stock launcher. Keep a rollback
    route and preserve Google Cast, system updates and essential
    services.
4.  **Validate on the real TV:** Use remote-key inputs and screenshots
    to test directional navigation, app launching, HDMI inputs, casting
    and behavior after reboot. Fix problems before further debloating.

The repository reportedly documents remote-key injection and screenshot
capture for testing its launcher on a real television. (Original
citations: `turn710157search0`, `turn710157search6`.)

### Required access

-   **Route A --- Windows PC:** Enable ADB on the TV and use the
    repository's tools from the PC. This is the practical route because
    the PC can communicate with the TV over the home network.
-   **Route B --- Computer agent with terminal and network access:** If
    a supported computer-use or coding environment can reach both the
    repository and TV, it may perform the inspection, build and
    installation workflow, subject to permissions.

Neither route normally requires rooting the TV under CleanYourTV's
workflow. ADB authorization and compatible firmware are still required.

The assistant clarified that it can author and review commands and code
here, but cannot independently connect to the LAN, execute commands on
the television, or verify its live screen without an appropriate
connected execution environment.

------------------------------------------------------------------------

## 4. User: Clarifies which REA repository

**User**

> the other repo I'm referring is /rea reverse engineer anything

**Assistant**

The intended project is **REA: Reverse Engineer Anything**, not
CleanYourTV's own tools. The assistant identified it as [realkeisers/rea
on GitHub](https://github.com/realkeisers/rea). (Original citations:
`turn710822search0`, `turn710822search1`.)

That changes the approach.

### Could REA help?

REA is described as an agent-powered reverse-engineering toolkit. It
connects an AI coding agent to tools that inspect software behavior and
trace code, including supported native binaries and application
internals. An agent can use that evidence to explain behavior and build
a similar implementation. Its capabilities depend on the target type and
configured analysis providers; it does not automatically understand
every Android application.

For the TCL 55C635, a potential workflow:

1.  **Observe the original launcher:** Capture Home screen, navigation,
    remote-key behavior, transitions, app launching and recommendations.
    Inspect the launcher APK and resources if obtainable.
2.  **Use REA to investigate the implementation:** With authorized
    access to the APK and suitable tools, trace relevant code and
    inspect resources.
3.  **Build our own launcher:** Reproduce useful interaction patterns
    while removing clutter: clean dock, app grid, fast D-pad navigation,
    custom wallpaper and no unnecessary promotional content.
4.  **Test on the real TV:** Install the new APK, verify navigation and
    casting, and retain a route back to the original launcher.

REA is an analysis toolkit, not a magic bridge into the TV. The workflow
still needs a computer running REA with a compatible AI agent and
analysis tools, authorized access to the TV's software (for example,
launcher APK and, where supported, ADB), an Android APK build
environment, and a controlled installation/recovery process.

The assistant recommended building an independent launcher rather than
patching TCL's system launcher, as this is generally easier to maintain
and roll back and reduces the risk of breaking system components.

The assistant asked whether the goal was to reproduce TCL's launcher but
remove ads, or replace it entirely with a custom launcher.

------------------------------------------------------------------------

## 5. User: Chooses a completely custom launcher

**User**

> I'll pick the latter. because right now, i do not have that much of
> ads showing around that irritated me. it's all fine. my main goal was
> YouTube ads, but since the repo cant do anything about it, who am i to
> be choosy.

**Assistant**

The assistant agreed that a custom launcher was the better direction if
the stock TCL interface is not currently a problem. It recommended
treating the launcher as a separate project and keeping YouTube ad
mitigation separate so the TV is not compromised while trying to solve
two different problems.

### Proposed custom launcher for the TCL 55C635

-   A clean, dark home screen with favorite apps in a dock, a simple app
    grid, fast D-pad navigation and no unnecessary animation.
-   Custom wallpaper, configurable layout and a TV-remote-first
    interface.
-   Preserve Google Cast and treat ad/tracker blocking as a separate
    optional step.

### How REA might be used

The goal would not be to copy every line of TCL's launcher code. Use
reverse engineering selectively to answer concrete questions:

1.  How does the original launcher respond to the Home button and
    remote-control events?
2.  Which Android APIs expose installed apps, icons, labels and launch
    intents?
3.  How can the launcher handle HDMI inputs and system settings without
    interfering with TCL services?
4.  What does the TV firmware permit when choosing a third-party Home
    application?

Then build an independent implementation, install it alongside the
original and test it before making it the default.

A caveat: Android TV launchers cannot necessarily reproduce every
manufacturer-specific feature. Input switching, ambient modes and
certain system panels may rely on privileged TCL components. Integrate
with those features where permitted rather than assuming they can all be
recreated.

### YouTube ads

The assistant said not to give up entirely, but to evaluate alternatives
separately. Possible approaches include alternative YouTube clients for
Android TV and network-level filtering, each with trade-offs involving
account support, playback quality, updates and reliability. A method
should not be promised until verified on the C635's firmware.

**Recommendation:** Build the launcher first as a standalone APK,
without root or debloating. Keep the stock launcher as the recovery
path. Once the custom interface is stable, investigate YouTube
separately. This yields a useful project even if the YouTube-ad problem
remains unsolved.

------------------------------------------------------------------------

## 6. User: Requests this conversation as a Markdown file

**User**

> can you save this entire convo into an md file so that i can share it
> with other agents.

This document is the requested Markdown handoff. The following summary
is included for agents who need the key decisions without reading the
full transcript.

------------------------------------------------------------------------

# Agent Handoff Summary

## User's stated goals and constraints

-   **TV model:** TCL 55C635.
-   **Operating system:** Google TV.
-   **Primary objective:** Replace the stock launcher entirely with a
    custom-designed Android TV launcher.
-   **Secondary objective:** Explore options for YouTube ads;
    CleanYourTV's launcher/debloat/DNS features alone are not expected
    to reliably block YouTube in-stream ads.
-   **Must preserve:** Chromecast / Google Cast.
-   **Current state:** The user is not planning to install or change
    anything immediately. The TV currently works and its home-screen
    advertising is not especially irritating.
-   **Preferred approach:** Explore the project and architecture first;
    no urgent modifications.
-   **Rooting:** Not desired or required for the initial proposed
    workflow.
-   **Safety priority:** Do not disable packages or alter the stock
    launcher until the actual firmware and package inventory are
    understood. Keep a reliable rollback route.

## Repositories

-   CleanYourTV: https://github.com/Gathin23/cleanyourtv
-   REA (Reverse Engineer Anything): https://github.com/realkeisers/rea

**Important:** The repository URL for REA was identified in the
conversation as `realkeisers/rea`. Agents should independently verify
the correct repository and current contents before relying on this
identification or its capabilities.

## Proposed technical direction

1.  Review CleanYourTV as a reference for Android TV launcher behavior
    and relevant setup conventions, not as a package list to copy
    blindly.
2.  Review REA's actual supported analysis workflows, agent integration,
    target types and tool requirements. Do not assume it automatically
    reverse-engineers Android APKs or can connect to the TV.
3.  Obtain the exact TCL firmware/build and launcher package details
    later, when the user is ready.
4.  If appropriate, use ADB to inspect the device read-only and obtain
    the original launcher APK/resources only through authorized means.
5.  Design an independent native Android TV launcher with a clean dark
    UI, app dock/grid, D-pad navigation, app discovery and launch, and
    optional customization.
6.  Install and test alongside the stock launcher first. Do not disable
    the stock launcher until Home-button behavior and recovery are
    proven.
7.  Test Google Cast, streaming apps, remote control, HDMI/input
    behavior, reboot behavior and firmware compatibility.
8.  Consider minimal debloating only if it provides a clear benefit and
    each target package is understood.
9.  Investigate YouTube ad mitigation separately; do not claim DNS
    filtering will reliably remove YouTube ads.

## Open questions for future technical work

-   What exact Android version, firmware build, device codename and
    launcher package are on this 55C635?
-   Can a third-party launcher be installed and selected as Home on this
    particular firmware?
-   What launcher customization is needed: theme, layout, dock,
    wallpaper, app sorting, shortcuts, settings/input access?
-   Which Android TV APIs or TCL-specific components are required for
    inputs and other system functions?
-   Which REA tools/providers are compatible with the launcher APK and
    Android artifacts?
-   What YouTube-on-TV ad mitigation methods are currently compatible
    with this TV, and what are their security, privacy, update and
    account-support trade-offs?

## Safety rules for the next agent

-   Do not run debloat scripts or disable packages as an exploratory
    first step.
-   Do not assume another TCL model's package list matches the 55C635.
-   Do not expose ADB to the public internet.
-   Keep Cast, Google Play services, system updates, remote/input
    components and uncertain dependencies enabled until verified.
-   Preserve original package inventory, exact changes and rollback
    artifacts.
-   Do not claim installation or live-TV inspection unless it actually
    happened.
-   Separate reverse-engineering research from changes to the user's
    working TV.
