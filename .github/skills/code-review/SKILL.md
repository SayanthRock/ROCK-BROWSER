ROCK Browser — Skill Specification

1. Skill Identity

Skill Name: ROCK Browser
Skill ID: "rock-browser"
Version: "1.0.0"
Purpose: Build, maintain, test, and improve the ROCK Browser Android application.

ROCK Browser is a modern, privacy-conscious Android web browser with a polished ROCK design system and optional GitHub integration.

---

2. Core Responsibilities

The ROCK Browser skill should be able to:

- Build the Android application.
- Maintain the browser UI and UX.
- Manage tabs and navigation.
- Load websites using Android WebView.
- Handle downloads.
- Manage browser history.
- Manage bookmarks.
- Provide private/incognito browsing.
- Provide browser settings.
- Integrate GitHub securely.
- Display GitHub profiles, repositories, releases, and related information.
- Maintain a consistent ROCK visual identity.
- Test application behavior.
- Fix build errors.
- Improve accessibility and performance.
- Prepare release builds.

---

3. Platform

Primary Platform

Android

Minimum

- Android 10+

Recommended

- Latest stable Android SDK
- Kotlin
- Jetpack Compose
- AndroidX
- Material 3

Architecture

Use:

MVVM
Clean Architecture principles
Repository pattern
Dependency Injection
Coroutines
StateFlow

Avoid unnecessary abstraction.

---

4. ROCK Design System

ROCK Browser should have a distinctive premium interface.

Visual Direction

- Minimal
- Modern
- Dark-first
- Glassmorphism
- Liquid-glass inspired surfaces
- Rounded corners
- Strong typography
- Clean spacing
- Subtle animations
- High contrast
- Mobile-first

Design Principles

Simple
Fast
Premium
Readable
Responsive
Consistent

Avoid:

- Excessive gradients
- Excessive animations
- Cluttered toolbars
- Tiny controls
- Unnecessary dialogs
- Heavy visual effects that reduce performance

---

5. Main Browser Screen

The main screen should contain:

Top Area

- Address/search bar
- Back
- Forward
- Reload/stop
- Secure connection indicator
- Page loading indicator

Browser Content

- WebView
- Pull-to-refresh where appropriate
- Loading state
- Error state
- Offline state

Bottom Navigation / Toolbar

Provide quick access to:

- Back
- Forward
- Home
- Tabs
- Menu

The toolbar should automatically adapt to:

- Light theme
- Dark theme
- System theme

---

6. Address Bar

The address bar must support:

- URLs
- Search queries
- HTTPS
- HTTP
- Domain completion
- Search suggestions
- Copy URL
- Share URL
- Reload
- Page information

Example:

https://github.com/SayanthRock

If the user enters:

github rock

the browser should treat it as a search query.

---

7. Navigation

Implement:

- Back navigation
- Forward navigation
- Reload
- Stop loading
- Open link
- Open link in new tab
- Open link in private tab
- External application handling

Use Android lifecycle-aware navigation.

Do not crash when:

- A page redirects.
- A page fails to load.
- WebView is destroyed.
- The application returns from background.
- A URL uses an unsupported scheme.

---

8. Tab System

ROCK Browser must support multiple tabs.

Each tab should store:

Tab ID
URL
Title
Favicon
Loading state
Private state
WebView state

Features:

- New tab
- Close tab
- Switch tab
- Close all tabs
- Restore tabs where possible
- Private tabs
- Tab count indicator

Tab UI should use a modern card/grid interface.

---

9. Home Page

The ROCK Browser home page should contain:

- ROCK Browser branding
- Search/address field
- Quick links
- Recent websites
- Bookmarks
- GitHub quick access
- Optional frequently visited sites

The user should be able to customize quick links.

---

10. Bookmarks

Implement:

- Add bookmark
- Remove bookmark
- Edit bookmark
- Bookmark folders
- Search bookmarks
- Open bookmark
- Share bookmark

Bookmark data should be persisted locally.

---

11. History

Implement:

- Browser history
- Search history
- History timestamps
- Search history
- Delete individual entries
- Clear all history

Privacy mode must never write private browsing history.

---

12. Private Browsing

Private mode should:

- Use a separate tab state.
- Avoid persistent history.
- Avoid persistent cookies where practical.
- Avoid saving form data.
- Avoid saving passwords.
- Clearly indicate private mode.

Private tabs must be visually distinguishable.

---

13. Downloads

Implement Android download support.

Features:

- Download detection
- Download progress
- Download notifications
- Download list
- Open downloaded file
- Share downloaded file
- Delete downloaded file
- Download error handling

Respect Android scoped storage.

Never request unnecessary storage permissions.

---

14. Permissions

Request permissions only when required.

Potential permissions:

INTERNET
POST_NOTIFICATIONS

Do not request:

READ_CONTACTS
ACCESS_FINE_LOCATION
CAMERA
MICROPHONE

unless a specific browser feature genuinely requires them.

Website permissions should be handled independently from application permissions.

---

15. WebView Security

Configure WebView securely.

Requirements:

- Enable HTTPS support.
- Disable unnecessary file access.
- Restrict universal access from file URLs.
- Validate external schemes.
- Avoid JavaScript interfaces unless absolutely required.
- Never expose secrets through JavaScript interfaces.
- Do not disable TLS certificate validation.
- Do not silently ignore SSL errors.

Never implement:

handler.proceed()

for SSL errors without explicit security justification.

---

16. WebView Features

Support:

- JavaScript
- DOM storage
- Cookies
- Local storage
- Downloads
- File uploads
- Multiple windows where appropriate
- Geolocation permissions
- Camera/microphone permissions when requested by a site

Permission requests must be shown clearly to the user.

---

17. GitHub Integration

GitHub integration is optional.

The browser should remain fully usable without GitHub.

GitHub features:

- GitHub login
- GitHub profile
- Repositories
- Repository details
- Releases
- Release assets
- Stars
- Followers
- Following
- Organizations
- GitHub quick access

---

18. GitHub Authentication

Use secure OAuth.

Preferred flow:

OAuth 2.0 Authorization Code
+
PKCE

Store sensitive credentials using:

Android Keystore

Never:

- Hardcode client secrets.
- Store tokens in SharedPreferences as plain text.
- Log access tokens.
- Include tokens in URLs.
- Commit secrets to GitHub.

Authentication state must survive application restarts securely.

---

19. GitHub API Client

Create a dedicated API layer.

Example:

GitHubRepository
GitHubApi
GitHubAuthManager
GitHubUser
GitHubRepositoryModel
GitHubRelease
GitHubReleaseAsset

API errors should be represented clearly.

Handle:

401 Unauthorized
403 Rate Limited
404 Not Found
409 Conflict
422 Validation Error
500 Server Error
Network unavailable
Timeout

---

20. GitHub Quick Access

Home page can include:

GitHub
My Profile
Repositories
Releases
Stars
Organizations

Quick access must be optional.

Users should be able to disable GitHub features.

---

21. Settings

Settings should include:

Appearance

- System
- Light
- Dark

Browser

- Homepage
- Search engine
- Default behavior for links
- Clear browsing data
- Downloads
- JavaScript
- Cookies

Privacy

- Private browsing
- History
- Tracking-related controls
- Site permissions

GitHub

- Connect GitHub
- Disconnect GitHub
- GitHub quick access
- GitHub account information

About

- ROCK Browser version
- Open-source licenses
- GitHub repository
- Privacy policy
- Feedback

---

22. Search Engine

Allow the user to select a search engine.

Architecture should make search providers configurable.

Example:

Google
Bing
DuckDuckGo
Brave Search
Custom

Do not hardcode the search engine throughout the application.

---

23. Error Pages

Create polished native browser error screens.

States:

No Internet

You're offline

Check your internet connection and try again.

Page Error

This page couldn't be loaded.

Try again

SSL Error

Secure connection failed.

For your safety, ROCK Browser did not continue.

Never encourage users to bypass certificate errors casually.

---

24. Loading Experience

Use a lightweight loading animation.

Recommended:

- Thin progress indicator
- Subtle shimmer
- Smooth transitions

Avoid long blocking splash screens.

Application startup should be fast.

---

25. Animations

Animations should be:

- Fast
- Smooth
- Functional

Use animations for:

- Tab opening
- Tab closing
- Toolbar transitions
- Page loading
- Menu appearance
- Theme changes

Avoid animations that interfere with navigation.

Respect:

Reduced motion

where platform accessibility settings make this appropriate.

---

26. Accessibility

Support:

- TalkBack
- Content descriptions
- Minimum touch target sizes
- Dynamic font scaling
- High contrast
- Keyboard navigation where applicable
- Clear focus states

Do not communicate important information through color alone.

---

27. Performance

Prioritize:

- Fast startup
- Low memory usage
- Efficient WebView lifecycle
- Lazy UI rendering
- Efficient image loading
- Minimal recomposition
- Background work optimization

Avoid memory leaks.

Pay special attention to:

WebView
Activity
Fragment
Lifecycle
Coroutine
Context

---

28. Offline Behavior

The browser should gracefully handle:

- No internet
- DNS failure
- Timeout
- Server unavailable

Local browser features must continue working offline:

- Bookmarks
- History
- Settings
- Saved tabs
- Download list

GitHub API operations should fail gracefully when offline.

---

29. Data Storage

Use appropriate Android storage technologies.

Suggested:

Room
DataStore
Android Keystore

Use Room for structured browser data.

Use DataStore for preferences.

Use Keystore for secrets.

---

30. Logging

Development logging may include:

Navigation events
WebView errors
API errors
Download state
Authentication state

Never log:

Access tokens
Refresh tokens
Passwords
Cookies
Private browsing URLs
Sensitive personal information

Disable verbose debugging logs in release builds.

---

31. Architecture

Recommended project structure:

app/
├── data/
│   ├── local/
│   ├── remote/
│   ├── github/
│   └── repository/
│
├── domain/
│   ├── model/
│   ├── repository/
│   └── usecase/
│
├── ui/
│   ├── browser/
│   ├── home/
│   ├── tabs/
│   ├── bookmarks/
│   ├── history/
│   ├── downloads/
│   ├── github/
│   ├── settings/
│   └── components/
│
├── navigation/
├── security/
├── utils/
└── MainActivity.kt

Keep browser logic separate from GitHub logic.

---

32. State Management

Use immutable UI state where practical.

Example:

data class BrowserUiState(
    val currentUrl: String = "",
    val pageTitle: String = "",
    val isLoading: Boolean = false,
    val progress: Int = 0,
    val canGoBack: Boolean = false,
    val canGoForward: Boolean = false
)

Expose state using:

StateFlow

Avoid global mutable state.

---

33. Testing

Every major feature should have tests.

Unit Tests

Test:

- URL parsing
- Search query detection
- Bookmark repository
- History repository
- GitHub API mapping
- Settings
- Authentication state

UI Tests

Test:

- Browser startup
- Navigation
- New tab
- Close tab
- Settings
- Bookmarks
- Private mode
- GitHub login flow

Security Tests

Verify:

- Tokens are not logged.
- Secrets are not hardcoded.
- SSL errors cannot be bypassed automatically.
- Private browsing does not persist history.

---

34. Build Requirements

The project must build using:

./gradlew assembleDebug

and:

./gradlew assembleRelease

Before release:

./gradlew lint
./gradlew test

Fix all blocking errors.

Do not hide compiler warnings by suppressing them without understanding the cause.

---

35. GitHub Actions

Recommended CI pipeline:

Checkout
↓
Setup JDK
↓
Setup Android SDK
↓
Gradle cache
↓
Lint
↓
Unit tests
↓
Build Debug
↓
Build Release
↓
Upload artifacts

Use maintained GitHub Actions versions.

Do not store signing keys directly in the repository.

Use GitHub Actions Secrets for release signing.

---

36. Release Build

Release builds should include:

- R8 where appropriate
- Resource shrinking where safe
- Proper signing
- Version name
- Version code
- Release notes

Before publishing:

Build succeeds
Tests pass
Lint passes
No secrets committed
No debug logging
No accidental permissions
No obvious crashes

---

37. Crash Handling

The application must gracefully handle:

- WebView crashes
- Network failures
- GitHub API failures
- Activity recreation
- Configuration changes
- Low-memory conditions
- Invalid URLs
- Unsupported URL schemes

Never terminate the entire application because one webpage failed.

---

38. External URLs

Handle schemes such as:

https://
http://
mailto:
tel:
intent:
market:

Validate external intents before launching them.

Do not blindly launch arbitrary intents.

---

39. Privacy

ROCK Browser should follow a privacy-first design.

Do not collect data unless required.

Do not send browsing history to ROCK servers.

Do not transmit private browsing information.

GitHub authentication data should only be used for the requested GitHub functionality.

Provide a clear privacy policy.

---

40. Branding

Application name:

ROCK Browser

Brand identity:

ROCK

Use consistent branding across:

- App icon
- Splash
- Home screen
- Settings
- About page
- GitHub repository
- README
- Release builds

Do not imitate Chrome, Edge, Safari, Firefox, or another browser's branding.

---

41. Definition of Done

ROCK Browser is considered complete when:

- [ ] Android project builds successfully.
- [ ] App launches without crashing.
- [ ] Web pages load correctly.
- [ ] Address bar works.
- [ ] Navigation works.
- [ ] Tabs work.
- [ ] Bookmarks work.
- [ ] History works.
- [ ] Private mode works.
- [ ] Downloads work.
- [ ] Settings work.
- [ ] Dark/light/system themes work.
- [ ] GitHub integration is optional.
- [ ] GitHub authentication is secure.
- [ ] GitHub repositories load correctly.
- [ ] GitHub releases load correctly.
- [ ] No secrets are hardcoded.
- [ ] No sensitive information is logged.
- [ ] Accessibility is implemented.
- [ ] Offline errors are handled.
- [ ] Unit tests pass.
- [ ] Lint passes.
- [ ] Debug APK builds.
- [ ] Release APK builds.
- [ ] GitHub Actions CI passes.

---

42. Agent Operating Rules

When modifying ROCK Browser:

1. Understand the existing project before changing architecture.
2. Make the smallest clean change necessary.
3. Preserve existing working functionality.
4. Follow Kotlin and Android best practices.
5. Keep UI consistent with the ROCK design system.
6. Never expose credentials or tokens.
7. Never bypass WebView security checks.
8. Never add unnecessary permissions.
9. Test changes before declaring them complete.
10. Fix the root cause rather than hiding errors.
11. Keep dependencies current and justified.
12. Avoid overengineering.
13. Prefer maintainable code over clever code.
14. Verify release builds before calling the application production-ready.

---

43. Final Product Goal

ROCK Browser should feel like a real, polished Android browser, not a WebView demo.

The final experience should be:

Fast
Clean
Private
Modern
Secure
GitHub-friendly
Android-native
ROCK-branded

The browser must remain useful as a standalone browser even when GitHub integration is disabled.
