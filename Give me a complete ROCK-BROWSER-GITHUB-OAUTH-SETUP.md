ROCK-BROWSER — GitHub OAuth Setup

Project: ROCK-BROWSER
Brand: SAYANTH ROCK
Platform: Android
Minimum Android: Android 10+
Authentication: GitHub OAuth 2.0 Authorization Code Flow with PKCE
Version: 1.0.0

---

1. Purpose

This document defines the complete GitHub authentication setup for ROCK-BROWSER.

The implementation must allow users to:

- Sign in with GitHub
- Authorize ROCK-BROWSER
- View their GitHub profile
- View repositories
- View starred repositories
- View releases
- Open GitHub repositories in ROCK-BROWSER
- Sign out securely
- Revoke/remove the locally stored GitHub authentication state

GitHub authentication is optional.

ROCK-BROWSER must remain a fully functional web browser without GitHub authentication.

---

2. Authentication Architecture

Use:

GitHub OAuth 2.0
Authorization Code Flow
+
PKCE
+
Android system browser / Custom Tabs
+
Android deep link
+
Secure token storage
+
GitHub REST API

Do not use an embedded WebView for GitHub authentication.

Authentication flow

┌───────────────────────┐
│     ROCK-BROWSER      │
└──────────┬────────────┘
           │
           │ Sign in with GitHub
           ▼
┌───────────────────────┐
│ Generate PKCE values  │
│ verifier + challenge  │
│ + state               │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│ Android System        │
│ Browser / Custom Tab  │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│       GitHub          │
│     Authorization     │
└──────────┬────────────┘
           │
           │ authorization code
           ▼
┌───────────────────────┐
│ rockbrowser://        │
│ oauth/callback        │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│ Validate state        │
│ Exchange code + PKCE  │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│ Secure token storage  │
│ Android Keystore      │
└──────────┬────────────┘
           │
           ▼
┌───────────────────────┐
│ GitHub REST API       │
└───────────────────────┘

---

3. GitHub OAuth Application

Go to:

https://github.com/settings/developers

Then:

Developer settings
→ OAuth Apps
→ New OAuth App

Create an application for ROCK-BROWSER.

Application name

ROCK Browser

Homepage URL

Use:

https://sayanthrock.github.io/ROCK-BROWSER/

If the project website changes, update this URL accordingly.

Authorization callback URL

Recommended custom URI:

rockbrowser://oauth/callback

The Android application must register this URI.

---

4. GitHub Client ID

After creating the OAuth application, GitHub provides:

Client ID

The client ID is not a password.

It can be included in the Android application.

Example:

GITHUB_CLIENT_ID=xxxxxxxxxxxxxxxxxxxx

Do not commit unnecessary environment-specific configuration.

---

5. Client Secret

Android applications must not contain a GitHub OAuth client secret.

Never place a GitHub client secret in:

AndroidManifest.xml

BuildConfig

strings.xml

local.properties

GitHub Actions artifacts

or Kotlin source code.

A value embedded in an Android APK should be considered recoverable.

PKCE is therefore required.

---

6. OAuth Endpoints

GitHub authorization endpoint:

https://github.com/login/oauth/authorize

GitHub token endpoint:

https://github.com/login/oauth/access_token

GitHub API:

https://api.github.com/

Authenticated user endpoint:

GET https://api.github.com/user

---

7. Required OAuth Parameters

The authorization request should contain:

client_id
redirect_uri
response_type
scope
state
code_challenge
code_challenge_method

Example:

https://github.com/login/oauth/authorize
    ?client_id=YOUR_CLIENT_ID
    &redirect_uri=rockbrowser%3A%2F%2Foauth%2Fcallback
    &response_type=code
    &scope=read%3Auser%20repo
    &state=RANDOM_STATE
    &code_challenge=PKCE_CHALLENGE
    &code_challenge_method=S256

Only request scopes actually required by ROCK-BROWSER.

---

8. OAuth Scopes

Use the minimum permissions necessary.

Recommended initial scope:

read:user

This is sufficient for basic profile access.

For private repository access, request:

repo

only when that functionality is actually required.

Do not request broad permissions by default.

Recommended permission strategy

Basic login:

read:user

Public repository browsing:

read:user

Private repository access:

read:user repo

If ROCK-BROWSER does not need private repository access, do not request "repo".

---

9. PKCE

ROCK-BROWSER must generate a cryptographically secure PKCE verifier.

Generate:

code_verifier

Then calculate:

code_challenge =
BASE64URL(
    SHA256(code_verifier)
)

Use:

code_challenge_method=S256

Never use:

plain

---

10. State Parameter

Every authentication request must generate a fresh random:

state

The state value protects against authorization request injection and CSRF-style attacks.

Store the state temporarily.

When the callback arrives:

received_state == stored_state

must be true.

Otherwise:

AuthenticationException

and the login flow must be aborted.

Never continue authentication when state validation fails.

---

11. Callback URI

ROCK-BROWSER uses:

rockbrowser://oauth/callback

Expected callback:

rockbrowser://oauth/callback?code=XXXX&state=YYYY

The application must:

1. Receive the URI.
2. Extract "code".
3. Extract "state".
4. Validate "state".
5. Retrieve the original PKCE verifier.
6. Exchange the authorization code.
7. Securely store the resulting token.
8. Clear temporary OAuth values.
9. Load the GitHub profile.
10. Update the UI.

---

12. Android Manifest

Register the OAuth callback using an intent filter.

Example:

<activity
    android:name=".MainActivity"
    android:exported="true">

    <intent-filter>
        <action android:name="android.intent.action.VIEW" />

        <category android:name="android.intent.category.DEFAULT" />
        <category android:name="android.intent.category.BROWSABLE" />

        <data
            android:scheme="rockbrowser"
            android:host="oauth"
            android:path="/callback" />
    </intent-filter>

</activity>

The exact activity architecture may differ depending on the final application structure.

---

13. Deep-Link Security

Do not accept arbitrary deep links as successful authentication.

Only accept:

scheme = rockbrowser
host = oauth
path = /callback

Reject:

rockbrowser://anything

rockbrowser://oauth/anything

https://example.com/oauth/callback

unless explicitly supported by the application.

Validate all callback parameters.

---

14. OAuth Manager

Create a dedicated authentication component.

Recommended structure:

auth/
├── GitHubAuthManager.kt
├── GitHubOAuthConfig.kt
├── GitHubOAuthState.kt
├── PkceGenerator.kt
├── GitHubTokenStore.kt
└── GitHubAuthRepository.kt

Responsibilities:

"GitHubAuthManager"

Controls:

- Login
- Callback handling
- Logout
- Authentication state

"PkceGenerator"

Creates:

- verifier
- challenge

"GitHubTokenStore"

Handles:

- Secure token storage
- Token retrieval
- Token deletion

"GitHubAuthRepository"

Handles GitHub API authentication.

---

15. OAuth State Model

Recommended model:

data class OAuthState(
    val state: String,
    val codeVerifier: String,
    val createdAt: Long
)

OAuth state should have a short lifetime.

For example:

5–10 minutes

Expired authentication attempts must be rejected.

---

16. Token Storage

GitHub authentication credentials must never be stored as plaintext.

Use:

Android Keystore
+
encrypted local storage

Recommended implementation:

Android Keystore
    ↓
encryption key
    ↓
Encrypted storage
    ↓
GitHub access token

Possible implementation technologies include:

- Android Keystore
- Jetpack Security
- encrypted preferences/datastore

The exact storage implementation can evolve without changing the authentication API.

---

17. Token Storage Interface

Use an abstraction such as:

interface GitHubTokenStore {

    suspend fun saveToken(token: String)

    suspend fun getToken(): String?

    suspend fun clearToken()

    suspend fun hasToken(): Boolean
}

This prevents UI code from directly handling secure storage.

---

18. GitHub API Client

Create:

github/
├── GitHubApi.kt
├── GitHubApiClient.kt
├── GitHubAuthInterceptor.kt
├── GitHubModels.kt
└── GitHubRepository.kt

The API client should automatically attach:

Authorization: Bearer <token>

or the authentication scheme supported by the current GitHub OAuth token implementation.

Also send:

Accept: application/vnd.github+json

and an identifiable:

User-Agent

such as:

ROCK-BROWSER

---

19. Initial API Endpoints

Current user

GET /user

Use this to display:

- Avatar
- Username
- Name
- Bio
- Followers
- Following
- Public repositories

---

Repositories

GET /user/repos

Support:

page
per_page
sort
direction

Use pagination.

Do not download thousands of repositories at startup.

---

Starred repositories

GET /user/starred

Use pagination.

---

Repository

GET /repos/{owner}/{repo}

---

Releases

GET /repos/{owner}/{repo}/releases

---

20. Repository UI

After authentication:

GitHub
├── Profile
├── Repositories
├── Starred
├── Releases
└── Settings

Repository cards should show:

Repository name
Description
Language
Stars
Forks
Updated time
Private/Public state

Selecting a repository opens:

Repository Details

with:

README
Files
Issues
Releases
GitHub URL
Open in Browser

---

21. GitHub Quick Access

ROCK-BROWSER should provide a GitHub Quick Access area.

Recommended shortcuts:

GitHub
Repositories
Starred
Profile
Releases
Notifications
GitHub Home

The user should be able to open GitHub web pages directly in ROCK-BROWSER.

---

22. Browser Integration

GitHub integration must not replace normal browser functionality.

Example:

User opens github.com
        ↓
ROCK-BROWSER WebView/browser engine
        ↓
Normal GitHub website

GitHub API integration is an additional native feature.

The application should never inject authentication cookies or tokens into arbitrary websites.

---

23. Authentication State

Create a simple state model:

sealed interface GitHubAuthState {

    data object SignedOut : GitHubAuthState

    data object Loading : GitHubAuthState

    data class SignedIn(
        val user: GitHubUser
    ) : GitHubAuthState

    data class Error(
        val message: String
    ) : GitHubAuthState
}

The UI should react to this state.

---

24. Login UI

Recommended button:

Continue with GitHub

or:

Sign in with GitHub

The UI should clearly state:

GitHub login is optional.

Do not make GitHub authentication mandatory for browsing.

---

25. Loading State

During authentication show:

Connecting to GitHub…

During profile loading:

Loading GitHub profile…

Avoid displaying raw network errors.

---

26. Error Handling

Handle at minimum:

User cancelled
Authorization denied
Invalid state
Invalid callback
Expired OAuth state
Network unavailable
GitHub unavailable
Invalid authorization code
Token exchange failed
Unauthorized API request
Rate limit exceeded
Malformed response

User-facing messages should be readable.

Example:

GitHub sign-in was cancelled.

instead of:

OAuthException: HTTP 400

Detailed diagnostic information may be logged in debug builds, but never include tokens or secrets.

---

27. Logout

Logout must:

1. Remove the locally stored GitHub token.
2. Clear cached GitHub user data.
3. Clear OAuth state.
4. Clear PKCE verifier.
5. Reset authentication state.
6. Return the UI to signed-out state.

Example:

GitHub
    ↓
Sign out
    ↓
Confirm
    ↓
Local credentials deleted

---

28. Account Removal

Provide an optional:

Remove GitHub connection

setting.

This should remove all local GitHub authentication data.

It should not delete the user's GitHub account.

---

29. No Token Logging

Never do:

Log.d("GitHub", token)

Never log:

Authorization headers
refresh credentials
OAuth codes
PKCE verifier
client secrets

Also ensure HTTP logging libraries do not expose authorization headers.

---

30. Network Security

Use HTTPS for all network communication.

Allowed production endpoints:

https://github.com/
https://api.github.com/

Do not allow:

http://

for GitHub authentication or API traffic.

---

31. WebView Rules

GitHub OAuth must not run inside a normal embedded WebView.

Use:

Android Custom Tabs

or the device's system browser.

Benefits:

- Better security
- Better compatibility
- GitHub authentication support
- User-controlled browser session
- Reduced credential exposure

---

32. Client Secret Rule

This is a hard project requirement:

«No GitHub OAuth client secret may exist in the Android application.»

If a future architecture requires a client secret, introduce a backend authentication service.

The Android application must communicate with that backend rather than embedding the secret.

---

33. Optional Backend Architecture

A backend is not required for the initial PKCE implementation if GitHub's current OAuth configuration supports the intended public-client flow.

If a backend becomes necessary:

ROCK-BROWSER Android
        │
        ▼
ROCK Auth Service
        │
        ▼
GitHub OAuth

Never put:

GITHUB_CLIENT_SECRET

inside the APK.

Store server secrets only in:

Backend environment variables

or a secure secret manager.

---

34. Configuration

Recommended configuration:

object GitHubOAuthConfig {

    const val CLIENT_ID = BuildConfig.GITHUB_CLIENT_ID

    const val AUTHORIZATION_URL =
        "https://github.com/login/oauth/authorize"

    const val TOKEN_URL =
        "https://github.com/login/oauth/access_token"

    const val REDIRECT_URI =
        "rockbrowser://oauth/callback"

    const val API_BASE_URL =
        "https://api.github.com/"
}

Do not include a client secret.

---

35. Build Configuration

For local development, configure the client ID through Gradle/build configuration.

Example concept:

GITHUB_CLIENT_ID

The final implementation should avoid committing developer-specific configuration unnecessarily.

GitHub Actions should provide environment-specific configuration where appropriate.

---

36. GitHub Actions

CI should:

Checkout
↓
Set up JDK
↓
Set up Android SDK
↓
Build
↓
Run tests
↓
Run lint
↓
Generate APK/AAB

Do not print OAuth configuration or secrets into build logs.

If a backend is introduced, backend secrets must be stored using:

GitHub Actions Secrets

or an equivalent secure secret manager.

---

37. Testing

Authentication must be tested on:

Android 10
Android 11
Android 12
Android 13
Android 14
Android 15

and newer supported Android versions.

Test:

Login

Open app
→ GitHub
→ Authorize
→ Callback
→ Profile displayed

Cancel

Open GitHub
→ Cancel
→ Return to app
→ Remain signed out

Invalid state

Callback with incorrect state
→ Reject authentication

Missing code

Callback without code
→ Reject authentication

Network failure

Disable network
→ Attempt login/API request
→ Show useful error

Logout

Signed in
→ Sign out
→ Token removed
→ Signed out

---

38. Security Test Checklist

Before release, verify:

[ ] No client secret in APK
[ ] No access token in source
[ ] No access token in logs
[ ] No OAuth code in logs
[ ] PKCE enabled
[ ] S256 used
[ ] State generated randomly
[ ] State validated
[ ] OAuth state expires
[ ] Callback URI validated
[ ] HTTPS enforced
[ ] Tokens encrypted
[ ] Logout clears token
[ ] OAuth uses system browser/Custom Tabs
[ ] API authorization headers are not logged

---

39. GitHub API Rate Limits

The application must handle API rate limits gracefully.

Do not continuously poll GitHub.

Use:

Cache
Pagination
Refresh-on-demand
Reasonable retry logic

When GitHub reports a rate limit:

GitHub API limit reached.
Please try again later.

Do not retry aggressively.

---

40. Offline Behavior

ROCK-BROWSER should remain usable offline as a browser where applicable.

GitHub features should show:

You're offline.
GitHub data can't be refreshed right now.

Previously cached non-sensitive information may be displayed where appropriate.

Authentication tokens must remain protected.

---

41. Architecture

Recommended architecture:

UI
│
├── GitHubLoginScreen
├── GitHubProfileScreen
├── GitHubRepositoriesScreen
├── GitHubRepositoryScreen
└── GitHubSettingsScreen
│
▼
ViewModel
│
▼
GitHubRepository
│
├── GitHubAuthManager
├── GitHubApiClient
└── GitHubTokenStore
│
▼
Android Keystore

Use a clean separation between:

UI
Domain
Data
Authentication
Networking
Storage

---

42. Suggested Package Structure

com.sayanthrock.rockbrowser
│
├── auth
│   ├── GitHubAuthManager.kt
│   ├── GitHubOAuthConfig.kt
│   ├── GitHubOAuthState.kt
│   ├── GitHubTokenStore.kt
│   └── PkceGenerator.kt
│
├── github
│   ├── GitHubApi.kt
│   ├── GitHubApiClient.kt
│   ├── GitHubRepository.kt
│   └── GitHubModels.kt
│
├── ui
│   └── github
│       ├── GitHubScreen.kt
│       ├── GitHubProfileScreen.kt
│       ├── GitHubRepositoriesScreen.kt
│       ├── GitHubRepositoryScreen.kt
│       └── GitHubSettingsScreen.kt
│
└── MainActivity.kt

---

43. UI Design

Follow the ROCK-BROWSER design system.

Recommended characteristics:

Dark-first
Minimal
Modern
Rounded cards
Glass-like surfaces
Strong typography
Smooth transitions
Mobile-first
Accessible

GitHub screens should feel native to ROCK-BROWSER rather than looking like an unrelated GitHub client.

---

44. GitHub Profile Card

Example:

┌─────────────────────────────┐
│       GitHub Profile        │
│                             │
│       [Avatar]              │
│       Sayanth Rock          │
│       @username             │
│                             │
│  Repositories   Followers   │
│       42           128      │
│                             │
│       View GitHub →         │
└─────────────────────────────┘

---

45. Settings

Add:

Settings
└── GitHub
    ├── Connected account
    ├── GitHub username
    ├── Permissions
    ├── Open GitHub
    ├── Remove connection
    └── Sign out

Never display the actual access token.

---

46. Privacy

ROCK-BROWSER should clearly explain:

GitHub authentication is optional.

ROCK-BROWSER uses GitHub authorization to provide
GitHub-related features such as profile, repository,
and release access.

Your GitHub credentials are handled by GitHub's
authentication page and are not collected by
ROCK-BROWSER.

Authentication tokens are stored securely on the device.

The final privacy policy should accurately reflect the actual implementation.

---

47. Data Minimization

Only store what is required.

Recommended locally stored information:

Encrypted:
GitHub access token

Non-sensitive/cacheable:
GitHub username
Profile information
Repository cache

Do not store:

GitHub password
GitHub login credentials
OAuth client secret
Unnecessary personal data

---

48. Release Requirements

Before publishing ROCK-BROWSER:

[ ] OAuth App configured
[ ] Callback tested
[ ] PKCE verified
[ ] State validation verified
[ ] Token storage audited
[ ] Release APK inspected
[ ] No client secret found
[ ] No credentials found in logs
[ ] GitHub API tested
[ ] Logout tested
[ ] Error handling tested
[ ] Privacy documentation updated

---

49. Troubleshooting

"redirect_uri_mismatch"

Check that the GitHub OAuth application's callback exactly matches:

rockbrowser://oauth/callback

Do not accidentally use:

rockbrowser:/oauth/callback

or:

rockbrowser://oauth/callback/

unless the application and GitHub configuration intentionally use that exact URI.

---

Callback does not open ROCK-BROWSER

Check:

AndroidManifest.xml

and verify the intent filter contains:

<action android:name="android.intent.action.VIEW" />
<category android:name="android.intent.category.DEFAULT" />
<category android:name="android.intent.category.BROWSABLE" />

and:

<data
    android:scheme="rockbrowser"
    android:host="oauth"
    android:path="/callback" />

---

"state" validation failure

Verify that:

Generated state

is the same value as:

Callback state

Do not accept the callback if they differ.

---

Token exchange failure

Check:

Client ID
Authorization code
PKCE verifier
Redirect URI
GitHub endpoint
Network connectivity

Never solve the problem by adding a client secret to the Android app.

---

GitHub API returns 401

Check:

Token exists
Token has not been revoked
Authorization header is correct
GitHub API URL is correct

If authentication is invalid:

Clear local token
Return to signed-out state
Ask the user to authenticate again

---

50. Definition of Done

GitHub OAuth integration is complete only when:

[✓] GitHub OAuth application configured
[✓] PKCE implemented
[✓] State protection implemented
[✓] Android callback implemented
[✓] System browser/Custom Tab used
[✓] Authorization code exchanged successfully
[✓] Token securely stored
[✓] GitHub profile loaded
[✓] Repository list loaded
[✓] Releases loaded
[✓] Logout implemented
[✓] Error states implemented
[✓] Rate-limit handling implemented
[✓] No credentials logged
[✓] No client secret embedded
[✓] Android 10+ tested
[✓] CI build succeeds
[✓] Privacy documentation updated

---

51. Final Security Rule

The most important ROCK-BROWSER GitHub authentication rule is:

«Never put a GitHub OAuth client secret or user access token in source code, APK resources, logs, GitHub repositories, screenshots, or public build artifacts.»

The intended production architecture is:

GitHub OAuth
      +
     PKCE
      +
System Browser / Custom Tabs
      +
Validated Deep Link
      +
Android Keystore-backed Token Storage
      +
GitHub REST API

This provides the foundation for secure GitHub integration while keeping ROCK-BROWSER usable as a normal browser without requiring GitHub authentication.

---

52. Official References

GitHub OAuth documentation:

https://docs.github.com/en/apps/oauth-apps

GitHub REST API:

https://docs.github.com/en/rest

GitHub REST API authentication:

https://docs.github.com/en/rest/authentication

Android security:

https://developer.android.com/privacy-and-security

Android deep links:

https://developer.android.com/training/app-links

Android Keystore:

https://developer.android.com/privacy-and-security/keystore

---

ROCK-BROWSER
SAYANTH ROCK

Secure by design.
Private by default.
GitHub integration optional.