ROCK-BROWSER-ANDROID-BUILD.md

ROCK BROWSER — Android Build Specification

«Project: ROCK BROWSER
Platform: Android
Language: Kotlin
Minimum Android Version: Android 10 / API 29
Target SDK: Android SDK 35
UI: Jetpack Compose + Material 3
Architecture: MVVM + Clean Architecture
Browser Engine: Android WebView / Chromium
GitHub Integration: Optional native GitHub dashboard + OAuth PKCE
Storage: Room + DataStore
Networking: Retrofit + OkHttp
Dependency Injection: Hilt/Dagger
License: MIT»

---

1. PROJECT OVERVIEW

ROCK BROWSER is a modern Android web browser built around a fast Chromium-based browsing engine and a premium mobile-first Jetpack Compose interface.

The application combines:

- Full web browsing
- Multi-tab browsing
- Private/incognito browsing
- Bookmarks
- History
- Downloads
- Site permissions
- Search and URL navigation
- Modern Liquid Glass-inspired UI
- Optional native GitHub integration
- GitHub repository browsing
- GitHub issues and pull requests
- GitHub OAuth authentication
- Native GitHub URL interception

The original project describes ROCK BROWSER as a production-grade Android browser with a secure Chromium-based engine, premium Compose UI, and optional native GitHub dashboard.

---

2. PRIMARY PRODUCT GOALS

The application must prioritize:

1. Fast browsing
2. Simple navigation
3. Reliable tab management
4. Privacy
5. Modern Android UI
6. One-handed usability
7. Stable state restoration
8. Secure GitHub authentication
9. Maintainable architecture
10. Production-ready builds

The browser should feel lightweight while still providing advanced features.

---

3. SUPPORTED DEVICES

Minimum

- Android 10
- API 29

Target

- Android SDK 35

Recommended

- Phones
- Foldables
- Tablets

The supplied README specifies Android 10/API 29 or higher and SDK 35.

---

4. TECHNOLOGY STACK

Core

- Kotlin
- Android SDK
- Android WebView
- Chromium WebView engine

UI

- Jetpack Compose
- Material 3
- Compose Navigation
- Compose Animation

Architecture

- MVVM
- Clean Architecture
- Repository pattern
- StateFlow
- Kotlin Coroutines

Storage

- Room
- DataStore

Networking

- Retrofit
- OkHttp
- Kotlin serialization or another suitable JSON serializer

Dependency Injection

- Hilt preferred

The README specifically identifies Kotlin, Jetpack Compose Material 3, MVVM/Clean Architecture, Coroutines/StateFlow, Room, DataStore, Retrofit, OkHttp, and Hilt/Dagger.

---

5. PROJECT STRUCTURE

Use a modular structure similar to:

ROCK-BROWSER/
│
├── app/
│
├── core/
│   ├── common/
│   ├── database/
│   ├── datastore/
│   ├── network/
│   └── ui/
│
├── feature/
│   ├── browser/
│   ├── github/
│   └── settings/
│
├── docs/
│   └── screenshots/
│
├── gradle/
│
├── .github/
│   └── workflows/
│
├── README.md
├── ROCK-BROWSER-ANDROID-BUILD.md
├── SECURITY.md
├── CONTRIBUTING.md
├── LICENSE
└── settings.gradle.kts

This follows the modular organization defined by the supplied README.

---

6. APPLICATION MODULE

The "app" module is responsible for:

- Application initialization
- Dependency injection
- Navigation
- Global theme
- Activity setup
- WebView lifecycle coordination
- Global error handling
- Application-level state

Recommended package:

com.sayanthrock.rockbrowser

---

7. BROWSER FEATURE MODULE

Location:

feature/browser/

Responsibilities:

- Browser screen
- WebView management
- Tabs
- Navigation
- Address bar
- Search
- History
- Bookmarks
- Downloads
- Site permissions
- Incognito mode
- Browser settings

---

8. HOME SCREEN

The home screen should provide a clean starting point.

Components

- ROCK BROWSER branding
- Address/search field
- Search button
- Quick shortcuts
- Recent pages
- Bookmarks
- GitHub shortcut
- Settings shortcut
- Private browsing shortcut

Design

Use:

- Rounded surfaces
- Dark-theme optimization
- Strong typography
- Generous spacing
- Subtle transparency
- Lightweight animations

The README explicitly defines a minimalist dark-theme-oriented Liquid Glass design with rounded surfaces and clean typography.

---

9. ADDRESS BAR

The address bar must support both:

https://example.com

and:

search query

Behavior

If the input looks like a URL:

Open URL

Otherwise:

Search using configured search engine

Features

- URL normalization
- Domain autocomplete
- Search suggestions
- Clear button
- Loading indicator
- Secure connection indicator
- Page title
- Copy URL
- Share URL

The original README specifies a unified URL/search field, domain autocomplete, and configurable default search engine.

---

10. BROWSER NAVIGATION

Provide:

- Back
- Forward
- Reload
- Stop loading
- Home
- Tabs
- Share
- Bookmark
- Menu

Navigation must correctly synchronize with WebView state.

---

11. WEBVIEW ENGINE

Use Android's native WebView as the browser rendering engine.

Responsibilities:

- Page rendering
- JavaScript execution
- Navigation
- Downloads
- Permissions
- Cookies
- SSL handling
- File selection
- Media playback

The README defines the browser engine as Android's native WebView powered by Chromium.

---

12. WEBVIEW SECURITY

Configure WebView conservatively.

Required:

- Safe Browsing
- Mixed-content blocking
- Secure URL handling
- HTTPS preference
- Controlled JavaScript
- Controlled file access
- Controlled content access
- Permission handling
- SSL error handling

Do not blindly bypass SSL certificate errors.

The supplied README specifically requires mixed-content blocking, cross-origin tracking protections, and Safe Browsing.

---

13. TAB MANAGEMENT

The browser must support multiple tabs.

Each tab should maintain:

Tab ID
URL
Title
Favicon
Loading state
WebView state
Private/non-private state
Last active timestamp

Required actions

- Create tab
- Close tab
- Switch tab
- Duplicate tab
- Reopen closed tab
- Close all tabs
- Close other tabs
- Private tab
- Restore previous tabs

Tabs must avoid unnecessary page reloads.

The README specifically calls for state-retained tab switching to prevent page reloads and memory leaks.

---

14. TAB SWITCHER UI

Create a dedicated tab overview.

Each tab card should display:

- Page preview or thumbnail
- Page title
- Domain
- Close button
- Private indicator

Actions:

+ New Tab
Close
Switch
Close All
New Private Tab

Use smooth but lightweight Compose animations.

---

15. BACKGROUND TAB MANAGEMENT

Avoid keeping unlimited WebViews alive.

Implement lifecycle-aware management.

Consider:

- Active WebView remains fully active
- Background tabs may be paused
- Memory pressure handling
- State restoration
- WebView cleanup after tab closure

Goal:

Fast switching
+
Low memory usage

---

16. PRIVATE / INCOGNITO MODE

Provide private browsing.

Private sessions must:

- Not appear in normal history
- Not create normal bookmarks unless explicitly requested
- Use separate session state
- Clear temporary browsing state when closed
- Display a clear private indicator

The README requires incognito functionality and states that private mode leaves no trace.

---

17. HISTORY

Use Room for browsing history.

Suggested entity:

HistoryEntity

id
url
title
domain
favicon
visitedAt
visitCount
isPrivate

Features:

- Recent history
- Search history
- Delete item
- Delete selected items
- Clear today
- Clear all
- Never store private sessions

---

18. BOOKMARKS

Use Room.

Suggested entity:

BookmarkEntity

id
url
title
folder
favicon
createdAt
updatedAt

Features:

- Add bookmark
- Remove bookmark
- Edit bookmark
- Create folders
- Move bookmark
- Search bookmarks
- Sort bookmarks

---

19. DOWNLOAD MANAGER

Support downloads initiated by WebView.

Provide:

- Download detection
- Download progress
- Notification
- Pause/resume where supported
- Cancel
- Open file
- Share file
- Download history

Create a dedicated Downloads screen.

---

20. SITE PERMISSIONS

Manage:

- Camera
- Microphone
- Location
- Notifications
- File access
- Other WebView-supported permissions

For every permission:

Allow
Ask
Block

Users must be able to review permissions from Settings.

---

21. SEARCH ENGINE

Allow a configurable default search engine.

Initial options can include:

- Google
- Bing
- DuckDuckGo
- Custom search URL

Do not hard-code the search engine into browser logic.

Create an abstraction:

interface SearchEngine {
    fun buildSearchUrl(query: String): String
}

---

22. ERROR HANDLING

Provide user-friendly browser errors.

Examples:

No Internet Connection
Page Cannot Be Loaded
Connection Timed Out
SSL Error
Unsupported URL
Download Failed
Permission Denied

Do not expose raw exceptions to users.

---

23. GITHUB INTEGRATION

GitHub integration is optional.

Create:

feature/github/

The GitHub feature must remain separable from core browsing.

The original README describes:

- Secure OAuth
- PKCE
- Native dashboard
- Repositories
- Issues
- Pull requests
- GitHub URL interception.

---

24. GITHUB AUTHENTICATION

Use OAuth with PKCE.

Never ship a confidential client secret inside the Android application.

Authentication flow:

User taps Sign in with GitHub
        ↓
Generate PKCE verifier/challenge
        ↓
Open GitHub authorization
        ↓
User approves
        ↓
Receive authorization callback
        ↓
Exchange authorization code
        ↓
Store authentication state securely
        ↓
Load GitHub dashboard

---

25. GITHUB DASHBOARD

The native dashboard should support:

Profile

- Avatar
- Username
- Name
- Bio
- Followers
- Following
- Public repositories

Repositories

- Repository list
- Search
- Language
- Stars
- Forks
- Description
- Updated date

Issues

- Open issues
- Closed issues
- Issue details

Pull Requests

- Open PRs
- Closed PRs
- PR details
- Repository information

---

26. GITHUB URL INTERCEPTION

When a user opens a supported GitHub URL, the browser may offer native rendering.

Examples:

github.com/user/repository
github.com/user/repository/issues
github.com/user/repository/pulls

Flow:

GitHub URL detected
        ↓
Parse URL
        ↓
Determine resource
        ↓
Offer/open native GitHub viewer

The README explicitly defines automatic native repository rendering for GitHub URLs.

Provide a setting:

Open GitHub links natively
[ON/OFF]

---

27. GITHUB API LAYER

Use:

Retrofit
OkHttp

Create:

GitHubApi
GitHubRepository
GitHubRemoteDataSource
GitHubLocalDataSource

Do not place API calls directly inside Compose screens.

---

28. NETWORK LAYER

Recommended structure:

core/network/
├── RetrofitProvider
├── OkHttpProvider
├── NetworkResult
├── NetworkError
└── ConnectivityMonitor

Handle:

- Timeout
- No connection
- HTTP errors
- Rate limits
- Authentication failures
- Server errors

---

29. DATA LAYER

Use repository interfaces.

Example:

UI
 ↓
ViewModel
 ↓
UseCase
 ↓
Repository
 ↓
Remote / Local Data Source

This keeps the application testable and maintainable.

---

30. DATABASE

Room should store:

- History
- Bookmarks
- Tabs where appropriate
- Download metadata
- GitHub cached information where useful

The supplied README explicitly assigns Room to bookmarks/history and DataStore to preferences.

---

31. DATASTORE

Use DataStore for:

- Theme
- Search engine
- Homepage
- Browser preferences
- GitHub native-link behavior
- Privacy settings
- Navigation preferences
- Download preferences

Avoid using DataStore for large relational datasets.

---

32. SETTINGS

Create:

Settings
├── General
├── Appearance
├── Search
├── Privacy
├── Permissions
├── Downloads
├── GitHub
├── Browser
└── About

---

33. APPEARANCE SETTINGS

Support:

System
Light
Dark

Also provide browser-specific visual preferences where practical.

Design direction:

- Dark luxury appearance
- Liquid Glass surfaces
- Rounded corners
- Clear hierarchy
- Minimal visual noise

---

34. LIQUID GLASS UI

The visual language should use:

- Translucent surfaces
- Rounded containers
- Layered depth
- Subtle blur where appropriate
- Soft borders
- Clean typography
- Dynamic but restrained lighting
- Smooth transitions

Do not overuse blur or transparency to the point that text becomes difficult to read.

---

35. MOBILE-FIRST NAVIGATION

Primary navigation should remain reachable with one hand.

Use a bottom navigation/control area.

Suggested controls:

Back
Home
Tabs
Menu

The README specifically calls for bottom-anchored controls for one-handed use.

---

36. ANIMATIONS

Animations should be:

- Fast
- Smooth
- Purposeful
- Battery-conscious

Use animations for:

- Tab opening
- Tab closing
- Navigation
- Loading
- Bottom sheet presentation
- Bookmark confirmation
- GitHub login state
- Page transitions

Avoid excessive animation during continuous scrolling.

---

37. ACCESSIBILITY

Support:

- Content descriptions
- Large font sizes
- Touch targets
- Screen readers
- High contrast
- Reduced motion where appropriate
- Keyboard navigation where applicable

Never communicate important state through color alone.

---

38. ORIENTATION

Support:

- Portrait
- Landscape

The UI must adapt to:

- Small phones
- Large phones
- Foldables
- Tablets

---

39. STATE MANAGEMENT

Use:

StateFlow
SharedFlow
CoroutineScope

Screens should expose predictable UI state.

Example:

data class BrowserUiState(
    val url: String = "",
    val title: String = "",
    val isLoading: Boolean = false,
    val progress: Int = 0,
    val canGoBack: Boolean = false,
    val canGoForward: Boolean = false,
    val error: BrowserError? = null
)

---

40. VIEWMODEL RULES

ViewModels must:

- Own screen state
- Call use cases
- Survive configuration changes
- Avoid direct Android View references
- Avoid direct database implementation
- Avoid direct Retrofit calls

---

41. CLEAN ARCHITECTURE

Use three conceptual layers:

Presentation
Domain
Data

Example:

BrowserScreen
     ↓
BrowserViewModel
     ↓
NavigateUseCase
     ↓
BrowserRepository
     ↓
WebView / Local Storage

---

42. UI COMPONENT LIBRARY

Create reusable components:

RockButton
RockIconButton
RockTextField
RockTopBar
RockBottomBar
RockGlassCard
RockDialog
RockBottomSheet
RockLoadingIndicator
RockTabCard
RockBrowserToolbar
RockEmptyState
RockErrorState

Keep styling centralized.

---

43. THEME SYSTEM

Create:

RockTheme
RockColors
RockTypography
RockShapes
RockDimensions
RockAnimations

Do not scatter hard-coded design values throughout the application.

---

44. BROWSER TOOLBAR

The toolbar should support:

[Back] [Address/Search] [Reload] [Tabs]

The exact layout may adapt depending on screen size.

The address field must show loading progress without becoming visually distracting.

---

45. MENU

The browser menu should include:

New Tab
New Private Tab
Bookmarks
History
Downloads
Share
Add Bookmark
Find in Page
Desktop Site
Settings
GitHub
About

Only show actions that are valid for the current page.

---

46. FIND IN PAGE

Provide:

- Search text
- Previous result
- Next result
- Match count
- Close

Use WebView's native find functionality.

---

47. DESKTOP SITE

Provide:

Request Desktop Site

Store the preference globally or per-site if implemented.

---

48. SHARING

Support Android Sharesheet.

Users should be able to share:

- Current URL
- Page title
- GitHub repository URL

---

49. COPY / PASTE

The address bar must support normal Android clipboard operations.

Provide:

- Copy
- Paste
- Cut
- Select all

---

50. FILE UPLOAD

Support web pages requesting file selection.

Handle:

- Images
- Documents
- Multiple files where supported

Respect Android storage and permission requirements.

---

51. MEDIA SUPPORT

Support standard WebView media behavior.

Handle:

- Video
- Audio
- Fullscreen video
- Media permissions

Ensure lifecycle handling when leaving and returning to the browser.

---

52. LIFECYCLE

Correctly handle:

onCreate
onStart
onResume
onPause
onStop
onDestroy

WebView resources must not leak.

Tabs must restore correctly after:

- Rotation
- Process recreation
- Backgrounding
- Memory pressure where possible

---

53. CRASH RESILIENCE

The application must avoid crashing when:

- WebView fails
- GitHub API fails
- Network disappears
- A page is malformed
- Download fails
- Permission is rejected
- OAuth is cancelled
- Activity is recreated

Show recovery UI instead.

---

54. PRIVACY

Privacy must be a core product principle.

Required:

- Private browsing
- Permission controls
- Safe Browsing
- Mixed-content protection
- Local storage controls
- Clear browsing data
- Transparent GitHub authentication

Do not collect unnecessary browsing data.

---

55. SECURITY

Security-sensitive data must never be logged.

Never log:

OAuth tokens
Authorization codes
Cookies
Passwords
Private URLs
Sensitive WebView data

Authentication data should use secure Android storage mechanisms where appropriate.

---

56. GITHUB TOKEN SECURITY

Never:

- Hard-code tokens
- Commit tokens
- Put secrets in Git
- Put client secrets in source
- Print authentication headers

Use secure storage.

---

57. NETWORK SECURITY

Use HTTPS wherever possible.

Do not bypass:

SSL certificate errors
Hostname validation
Authentication validation

Only permit insecure behavior when explicitly required and safely controlled.

---

58. LOGGING

Production builds must minimize sensitive logging.

Create a logging abstraction:

RockLogger

Debug builds may provide detailed diagnostics.

Release builds should remove or restrict sensitive logs.

---

59. TESTING

Implement:

Unit tests

- ViewModels
- Use cases
- Repositories
- Search engine
- URL parsing
- GitHub URL parsing

UI tests

- Home
- Browser toolbar
- Tabs
- Settings
- GitHub login
- GitHub dashboard

Integration tests

- Database
- GitHub API
- Navigation
- Tab restoration

---

60. BUILD VALIDATION

Before release:

./gradlew clean
./gradlew test
./gradlew lint
./gradlew assembleDebug
./gradlew assembleRelease

All required checks must pass.

---

61. BUILD REQUIREMENTS

Development environment:

- Android Studio Jellyfish or newer
- JDK 17+
- Android SDK 35
- Android 10+ test device/emulator

These requirements are directly specified in the supplied README.

---

62. DEBUG BUILD

Provide:

ROCK-BROWSER-debug.apk

Debug build should include:

- Development logging
- Debug diagnostics
- Non-production configuration

Never ship production credentials in debug configuration.

---

63. RELEASE BUILD

Provide:

ROCK-BROWSER-release.apk

Release build must:

- Be signed
- Disable debug behavior
- Minimize sensitive logs
- Use production configuration
- Pass tests
- Pass lint
- Pass build validation

The README specifies:

./gradlew assembleRelease

for producing a release APK.

---

64. GITHUB ACTIONS

Create CI workflows.

Suggested:

.github/workflows/
├── android-ci.yml
├── release.yml
└── dependency-check.yml

Android CI

Run:

Checkout
↓
Setup JDK
↓
Gradle cache
↓
Unit tests
↓
Lint
↓
Build
↓
Upload artifacts

---

65. RELEASE WORKFLOW

Release workflow should:

1. Trigger from GitHub release/tag
2. Checkout source
3. Configure Java
4. Restore Gradle dependencies
5. Run tests
6. Run lint
7. Build release APK
8. Upload APK
9. Publish release artifact

---

66. VERSIONING

Use:

MAJOR.MINOR.PATCH

Example:

1.0.0
1.0.1
1.1.0
2.0.0

Android "versionCode" must monotonically increase.

---

67. DOCUMENTATION

Maintain:

README.md
ROCK-BROWSER-ANDROID-BUILD.md
SECURITY.md
CONTRIBUTING.md
CHANGELOG.md

README should remain user-focused.

Build specification should remain developer-focused.

---

68. CONTRIBUTING

Follow the existing project contribution model:

git checkout -b feature/AmazingFeature
git commit -m "Add some AmazingFeature"
git push origin feature/AmazingFeature

Then open a Pull Request.

The supplied README defines this contribution workflow.

---

69. SECURITY REPORTING

Maintain:

SECURITY.md

Security vulnerabilities should not be reported through normal public issues.

The supplied README explicitly directs security researchers to "SECURITY.md".

---

70. LICENSE

The project uses the MIT License.

Maintain:

LICENSE

The supplied README identifies the project as MIT licensed.

---

71. REQUIRED MVP FEATURES

Version 1.0 must include:

Browser

- 
