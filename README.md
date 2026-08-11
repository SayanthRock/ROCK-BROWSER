ROCK BROWSER — ANDROID BUILD SPECIFICATION

«Project: ROCK BROWSER
Version: 1.0.0
Platform: Android 10+
Language: Kotlin
UI: Jetpack Compose + Material 3
Browser engine: Android Chromium-based WebView
Package: "com.sayanthrock.rockbrowser"
Brand: Sayanth Rock»

--- 

1. BUILD OBJECTIVE

Build a complete, production-quality Android web browser called ROCK BROWSER.

ROCK BROWSER combines:

- Real web browsing
- Modern mobile browser controls
- ROCK premium/Liquid Glass design
- Multiple tabs
- Search
- Bookmarks
- History
- Downloads
- Private browsing
- Site permissions
- Privacy controls
- Dark/light/system themes
- GitHub OAuth
- GitHub API integration
- GitHub repository/release access
- Android-native sharing
- Android file picker
- Production APK/AAB builds
- GitHub Actions CI/CD

This must be a functional browser, not a visual prototype.

Do not create fake browser functionality.

---

2. IMPORTANT PRINCIPLES

Follow these rules throughout development:

1. Build the application incrementally.
2. Keep code readable and maintainable.
3. Use modern Android APIs.
4. Avoid unnecessary dependencies.
5. Never hard-code secrets.
6. Never store passwords.
7. Never fake GitHub authentication.
8. Never bypass website security.
9. Never disable SSL certificate validation.
10. Never claim private browsing provides anonymity.
11. Keep GitHub integration optional.
12. Normal browsing must work without a GitHub account.
13. Handle errors gracefully.
14. Test every major feature.
15. Do not leave core features as placeholders.

---

3. TECHNOLOGY

Use:

Kotlin
Jetpack Compose
Material 3
AndroidX
Navigation Compose
ViewModel
StateFlow
Coroutines
Room
DataStore
Android WebView
Android Keystore
WorkManager where appropriate

Use current stable versions compatible with the project environment.

Do not use obsolete Android APIs when modern equivalents exist.

---

4. ANDROID CONFIGURATION

Application ID:

com.sayanthrock.rockbrowser

Application name:

ROCK BROWSER

Minimum SDK:

Android 10 / API 29

Target SDK:

Use the latest stable Android SDK available in the build environment.

Support:

- Portrait
- Landscape
- Gesture navigation
- Edge-to-edge
- Light mode
- Dark mode
- Dynamic screen sizes

---

5. PROJECT STRUCTURE

Use a clean modular architecture.

Recommended:

ROCK-BROWSER/
│
├── app/
│
├── core/
│   ├── common/
│   ├── database/
│   ├── datastore/
│   ├── network/
│   ├── security/
│   └── designsystem/
│
├── browser/
│   ├── engine/
│   ├── navigation/
│   ├── permissions/
│   └── downloads/
│
├── feature/
│   ├── home/
│   ├── tabs/
│   ├── bookmarks/
│   ├── history/
│   ├── downloads/
│   ├── private/
│   ├── settings/
│   ├── github/
│   └── about/
│
├── docs/
│
├── .github/
│   └── workflows/
│
├── README.md
├── LICENSE
├── SECURITY.md
├── CONTRIBUTING.md
├── CODE_OF_CONDUCT.md
└── CHANGELOG.md

Adapt the structure where necessary, but preserve separation of concerns.

---

6. ARCHITECTURE

Use:

Compose UI
    ↓
ViewModel
    ↓
Use Cases
    ↓
Repositories
    ↓
Data Sources
    ↓
WebView / Room / DataStore / GitHub API

Do not put business logic directly into Compose screens.

Use dependency injection where it provides clear value.

Avoid overengineering.

---

7. ROCK DESIGN SYSTEM

Create a centralized ROCK design system.

Design characteristics:

- Premium
- Minimal
- Modern
- Mobile-first
- Dark-theme friendly
- Liquid Glass inspired
- Rounded surfaces
- Soft elevation
- Strong typography
- Clean spacing
- Subtle animation

Do not make the UI look like a generic Material demo.

Material 3 should provide the foundation, while ROCK design tokens provide the visual identity.

---

8. COLORS

Dark theme:

Black
Charcoal
Deep Gray
White
Subtle metallic accent

Light theme:

White
Warm Gray
Charcoal
Subtle accent

Do not scatter raw colors throughout the application.

Create centralized theme tokens.

---

9. TYPOGRAPHY

Use a clean modern font system.

Provide:

- Large title
- Section title
- Body
- Label
- Caption

Maintain strong readability.

Respect Android font scaling.

Do not use extremely small text.

---

10. SHAPE SYSTEM

Use consistent rounded corners.

Recommended:

Small: 12dp
Medium: 16dp
Large: 20dp
Extra Large: 28dp

Use consistent spacing.

Avoid excessive rounded containers.

---

11. MAIN BROWSER SCREEN

Create the primary browser screen.

The browser should prioritize webpage content.

Structure:

┌─────────────────────────────┐
│                             │
│         WEB PAGE            │
│                             │
│                             │
│                             │
├─────────────────────────────┤
│  ←  →  ↻   Address      ⋮   │
├─────────────────────────────┤
│  Home       Tabs       Menu │
└─────────────────────────────┘

Adapt the layout depending on screen size.

---

12. ADDRESS BAR

Create a premium rounded address bar.

Placeholder:

Search or enter website address

Support:

- HTTPS URLs
- HTTP URLs
- Domains
- Search queries
- GitHub URLs
- IP addresses where appropriate
- URL normalization

Examples:

github.com
https://github.com/SayanthRock
youtube.com
Android development

If input is not a valid URL, search using the selected search engine.

---

13. SEARCH ENGINE

Default search engine:

Google

Allow:

- Google
- Bing
- DuckDuckGo
- Brave Search
- Ecosia
- Custom search engine

Search engine selection must be stored using DataStore.

Do not require a search API.

---

14. WEB ENGINE

Use Android's Chromium-based WebView for the first production version.

Support:

- HTML
- CSS
- JavaScript
- Cookies
- DOM storage
- HTTPS
- Local storage
- Web APIs supported by Android WebView
- Media playback
- File uploads
- Downloads
- Multiple windows where supported

Configure WebView carefully.

---

15. WEBVIEW SECURITY

Apply secure defaults.

Never:

Disable SSL certificate validation
Ignore certificate errors
Expose arbitrary JavaScript bridges
Allow unsafe file access unnecessarily
Execute arbitrary native commands
Store secrets inside WebView

Use:

- Safe Browsing where available
- HTTPS
- Origin validation
- Secure WebView settings
- Android permission controls

Certificate errors must be treated as security-sensitive.

---

16. BROWSER NAVIGATION

Implement:

- Back
- Forward
- Reload
- Stop
- Loading progress

Behavior:

Back:
WebView can go back → navigate back
Otherwise → follow normal Android navigation behavior

Reload becomes Stop while the page is loading.

---

17. TAB SYSTEM

Implement real multi-tab browsing.

Features:

- New tab
- Close tab
- Switch tab
- Duplicate tab
- Tab title
- Favicon
- Loading indicator
- Tab count
- Restore closed tab
- Persist normal tabs
- Private tabs

Example:

ROCK BROWSER

[ + ]

Google                         ×
google.com

GitHub                         ×
github.com

YouTube                        ×
youtube.com

---

18. TAB MANAGEMENT

Use a dedicated tab state model.

Example data:

Tab
- id
- url
- title
- favicon
- isPrivate
- isLoading
- progress
- canGoBack
- canGoForward
- lastAccessed

Do not store WebView objects directly inside persistent database entities.

---

19. TAB PERFORMANCE

Implement reasonable memory management.

Use:

- Lazy initialization
- Tab state restoration
- WebView cleanup when tabs close
- Memory-aware handling

Do not unnecessarily keep dozens of fully active WebViews alive.

Do not unexpectedly destroy active user sessions.

---

20. NEW TAB PAGE

Create a premium ROCK homepage.

Show:

ROCK BROWSER

[ Search or enter website address ]

Quick Access

Google
YouTube
GitHub
Gmail

Recent Sites

Bookmarks

Allow users to customize shortcuts.

Optional:

- Clock
- Wallpaper
- Recently visited sites

Keep startup fast.

---

21. BOOKMARKS

Use Room.

Features:

- Add bookmark
- Remove bookmark
- Edit bookmark
- Folder support
- Search
- Sort
- Move
- Delete
- Open
- Import/export where practical

Data:

Bookmark
- id
- title
- url
- favicon
- folderId
- createdAt
- updatedAt

---

22. HISTORY

Store history locally.

Data:

HistoryEntry
- id
- url
- title
- favicon
- visitedAt

Features:

- Search
- Open
- Delete item
- Delete today
- Delete date range
- Clear all

Provide a confirmation for destructive actions.

---

23. PRIVATE MODE

Create ROCK PRIVATE.

Private mode must use an isolated browser session where practical.

Do not persist normal:

- History
- Cookies
- Web storage
- Form data

after the private session ends, within the limits of Android WebView.

Clearly label private browsing.

Display a short explanation:

Private browsing prevents ROCK BROWSER
from saving normal local browsing history.

It does not make you anonymous online.

---

24. DOWNLOAD MANAGER

Support downloads initiated by websites.

Show:

Downloads

filename.zip
25 MB

████████░░ 80%

2.3 MB/s

Support:

- Progress
- Speed
- Status
- Cancel
- Retry
- Open
- Share
- Delete
- Show location

Use Android's appropriate download APIs.

---

25. FILE UPLOAD

Support website file uploads.

Use Android's Storage Access Framework.

Support:

- Images
- Videos
- Documents
- Multiple selection where supported

Do not request broad storage access unnecessarily.

---

26. CAMERA PERMISSION

When a website requests camera access:

1. Check Android permission.
2. Request Android permission if required.
3. Show clear user-facing explanation where appropriate.
4. Grant only when authorized.
5. Allow revocation through settings.

Never silently grant camera access.

---

27. MICROPHONE PERMISSION

Implement the same secure model for microphone access.

Support sites such as:

- Video conferencing
- Voice recording
- WebRTC applications

Only grant after user authorization.

---

28. LOCATION PERMISSION

Support website location requests.

Never silently grant location.

Allow users to revoke the permission.

---

29. NOTIFICATIONS

Where supported by Android WebView and the implementation:

Allow websites to request notifications.

Provide:

Allow
Block
Ask

Allow site-level management.

---

30. SITE PERMISSIONS

Create:

Settings
→ Site permissions

Categories:

Camera
Microphone
Location
Notifications
Pop-ups
Downloads
JavaScript
Cookies

Store site-specific settings securely.

---

31. POP-UPS

Handle popup/new-window requests.

Possible behavior:

Open in new tab
Open in current tab
Block

Do not allow abusive popup behavior to overwhelm the user.

---

32. COOKIES

Provide privacy controls:

Allow cookies
Block third-party cookies
Clear cookies
Clear site data

Do not unnecessarily break websites.

---

33. PRIVACY

Create a privacy section.

Include:

- Safe Browsing
- Cookie controls
- Clear browsing data
- Site permissions
- Do Not Track preference
- Private browsing
- Download controls

Explain what each feature actually does.

Never make unsupported privacy claims.

---

34. CLEAR BROWSING DATA

Provide:

Clear:

Browsing history
Cookies
Cached files
Site data
Downloads list

Allow:

Last hour
Last 24 hours
Last 7 days
Last 4 weeks
All time

Make destructive operations explicit.

---

35. GITHUB INTEGRATION

GitHub is an optional ROCK BROWSER feature.

Normal browser functionality must remain fully usable without GitHub.

Create:

Settings
→ GitHub

and a GitHub shortcut on the home page.

---

36. GITHUB LOGIN

Use official GitHub OAuth.

The ROCK BROWSER UI must NOT ask for:

GitHub password

Instead show:

Continue with GitHub

Launch GitHub's official authorization interface.

Use an Android-compatible secure OAuth architecture.

Prefer:

Authorization Code + PKCE

when supported by the selected GitHub OAuth application architecture.

---

37. OAUTH SECURITY

Implement:

- State validation
- PKCE where supported
- Secure redirect handling
- HTTPS in production
- Secure token storage
- Token expiration handling
- Revocation handling

Never:

- Embed client secrets in APK
- Put tokens in source code
- Log tokens
- Store tokens in plain preferences
- Send tokens to unrelated servers

Use Android Keystore-backed secure storage.

---

38. GITHUB ACCOUNT

After authentication:

GitHub

@username

Profile
Repositories
Starred
Issues
Pull Requests
Releases

Disconnect

Show:

- Avatar
- Username
- Display name
- Public profile information

Only request and display authorized information.

---

39. GITHUB API

Use the official GitHub API.

Support:

Profile

- Username
- Avatar
- Bio
- Followers
- Following

Repositories

- Name
- Description
- Stars
- Forks
- Language
- Topics
- Default branch

Releases

- Release name
- Tag
- Release notes
- Assets
- Download links

Issues

- List
- Open issue page

Pull Requests

- List
- Open PR page

Handle:

- 401
- 403
- 404
- Rate limits
- Network failures
- Offline state

---

40. GITHUB WEBSITE

"https://github.com" must remain a normal website.

Users can:

- Browse GitHub
- Sign in
- View repositories
- Search
- View source
- View issues
- View PRs
- View releases
- Download assets

Do not create a fake GitHub website.

---

41. GITHUB QUICK ACCESS

If authenticated, display:

GitHub

My Profile
Repositories
Starred
Issues
Pull Requests
Releases

Use real API data.

If unauthenticated:

Connect GitHub

[ Continue with GitHub ]

---

42. GITHUB REPOSITORY SCREEN

Optional native repository viewer.

Display:

Owner / Repository

Description

★ Stars
Forks
Issues

README
Files
Commits
Releases

Buttons:

Open on GitHub
Download Release

Never fake repository statistics.

---

43. GITHUB RELEASES

Create a release browser.

Show:

ROCK BROWSER
v1.0.0

Release notes

Assets

ROCK-BROWSER-release.apk
ROCK-BROWSER-source.zip

[Download]

Use actual GitHub release data.

---

44. GITHUB DISCONNECT

Provide:

Disconnect GitHub

Confirmation:

Disconnect GitHub?

Your local GitHub authentication
will be removed from ROCK BROWSER.

[Cancel] [Disconnect]

Delete stored authentication data securely.

---

45. GITHUB API RATE LIMITS

Detect API rate limiting.

Display:

GitHub API limit reached.

Please try again later.

[Retry]

Do not continuously retry and waste network resources.

---

46. OFFLINE MODE

When offline:

- Browser pages already available may remain visible
- Bookmarks remain accessible
- History remains accessible
- Settings remain accessible
- GitHub API calls show offline state
- Retry becomes available

Do not crash.

---

47. ERROR PAGES

Create ROCK-branded error states.

Examples:

No Internet

ROCK BROWSER couldn't connect.

[Try Again]

Page Unavailable

The website couldn't be loaded.

[Reload]

For certificate errors, show the actual security issue.

Never bypass certificate validation.

---

48. FIND IN PAGE

Implement a mobile find bar.

Features:

- Search
- Previous
- Next
- Match count
- Close

---

49. DESKTOP SITE

Add:

Desktop site

to the browser menu.

Allow users to toggle desktop user-agent behavior where supported.

Persist the setting appropriately.

---

50. SHARE

Use Android Sharesheet.

Share:

- URL
- Page title

Example:

Share page

---

51. ADD TO HOME SCREEN

Support installable web apps/PWA behavior where Android/WebView supports it.

Use proper Android mechanisms.

Do not fake app installation.

---

52. BROWSER MENU

Use a polished bottom sheet.

Items:

New tab
New private tab
Bookmarks
History
Downloads
Share
Find in page
Desktop site
Add to home screen
GitHub
Settings
About

Show icons and labels.

Use touch-friendly spacing.

---

53. SETTINGS SCREEN

Create a polished settings experience.

General

- Homepage
- Search engine
- Startup behavior

Appearance

- Light
- Dark
- System
- Accent
- Toolbar style

Tabs

- Restore previous tabs
- Tab layout
- New tab behavior

Privacy

- Cookies
- Safe Browsing
- Clear browsing data
- Site permissions

Downloads

- Download location
- Ask where to save

GitHub

- Connected account
- GitHub integration
- Disconnect

Advanced

- JavaScript
- Developer options
- Experimental features

---

54. THEME SYSTEM

Support:

Light
Dark
System Default

Persist the selection with DataStore.

Ensure every screen follows the theme.

---

55. ANIMATIONS

Use subtle animations:

- Tab open
- Tab close
- Tab switching
- Bottom sheet
- Address bar focus
- Loading
- Page transitions
- GitHub account connection

Animations must be:

- Fast
- Smooth
- Purposeful

Avoid unnecessary animation.

Respect accessibility preferences.

---

56. SPLASH SCREEN

Use Android's splash-screen API.

Display:

ROCK
BROWSER

Keep the splash short.

Do not delay startup unnecessarily.

---

57. ONBOARDING

First launch:

Welcome to ROCK BROWSER

Fast browsing.
Private controls.
ROCK design.
Optional GitHub integration.

[Get Started]

Allow:

Skip

GitHub must never be mandatory.

---

58. ACCESSIBILITY

Support:

- TalkBack
- Content descriptions
- Accessible buttons
- Keyboard navigation where applicable
- Large text
- Contrast
- Focus states
- 48dp minimum touch targets

Test with Android accessibility tools.

---

59. PERFORMANCE

Optimize for mobile.

Priorities:

1. Fast startup
2. Smooth scrolling
3. Fast tab switching
4. Low unnecessary memory use
5. Efficient database access
6. Efficient API requests
7. Low battery usage

Avoid unnecessary background work.

---

60. DATABASE

Use Room.

Implement migrations.

Never delete existing user data during an app update.

Use indexes for frequently searched fields.

---

61. DATASTORE

Use DataStore for:

Theme
Search engine
Homepage
Privacy preferences
Tab preferences
Download preferences
Onboarding state

---

62. SECURITY

Security requirements:

- Android Keystore
- Secure OAuth
- No hard-coded secrets
- No token logging
- No password storage
- Safe WebView configuration
- Secure network communication
- Minimal permissions
- Secure file handling

Run dependency/security checks where practical.

---

63. ANDROID PERMISSIONS

Only request permissions when required.

Possible permissions:

INTERNET
ACCESS_NETWORK_STATE
CAMERA
RECORD_AUDIO
ACCESS_FINE_LOCATION
ACCESS_COARSE_LOCATION
POST_NOTIFICATIONS

Do not add permissions without a clear feature requirement.

Avoid unnecessary storage permissions.

---

64. LOGGING

Use structured development logging.

Never log:

- Passwords
- OAuth tokens
- Cookies
- Sensitive personal data

Disable verbose debugging in release builds.

---

65. TESTING

Create unit tests for:

- URL parser
- Search engine
- Tab manager
- Bookmark repository
- History repository
- Settings
- GitHub API repository
- OAuth state handling

Create Android tests for:

- Browser screen
- Tab creation
- Tab switching
- Bookmark flow
- Settings
- Theme switching
- Private mode

---

66. BUILD CHECK

Run:

./gradlew clean
./gradlew lint
./gradlew test
./gradlew assembleDebug

Then:

./gradlew assembleRelease

Fix every blocking error.

Do not declare success if the APK does not build.

---

67. GITHUB ACTIONS

Create:

.github/workflows/android.yml

CI:

Checkout
↓
JDK setup
↓
Android SDK
↓
Gradle cache
↓
Lint
↓
Unit tests
↓
Build Debug APK
↓
Upload artifact

Release workflow:

Git tag
↓
Build Release
↓
Run tests
↓
Create GitHub Release
↓
Upload APK/AAB

Never commit signing keys.

Use GitHub Secrets for signing credentials.

---

68. RELEASE

Initial version:

1.0.0

Artifacts:

ROCK-BROWSER-debug.apk
ROCK-BROWSER-release.apk
ROCK-BROWSER-release.aab

Use proper release signing.

Do not distribute debug builds as production releases.

---

69. APP ICON

Create an adaptive launcher icon.

Requirements:

- ROCK branding
- Simple
- Premium
- Recognizable
- Dark/light compatible
- High resolution

Do not use a generic browser icon.

---

70. README

Create a professional "README.md".

Include:

- ROCK BROWSER description
- Features
- Screenshots
- Installation
- Build instructions
- Architecture
- GitHub integration
- Privacy
- Security
- Supported Android versions
- Contributing
- License

Add GitHub Actions status badges.

---

71. SECURITY FILES

Create:

SECURITY.md
CONTRIBUTING.md
CODE_OF_CONDUCT.md
LICENSE
CHANGELOG.md

Keep documentation accurate.

---

72. PRIVACY POLICY

Document:

- What browsing data is stored
- Where it is stored
- What GitHub data is acc