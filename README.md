# ROCK BROWSER

ROCK BROWSER is a fast, clean, and modern Android web browser featuring optional GitHub integration, a secure architecture, and an elegant Compose-based UI.

## Features
- **Fast Browsing**: Lightweight and optimized for modern Android devices.
- **Privacy Controls**: Incognito mode, clear site data, and cookie management.
- **Tabs**: Efficient multi-tab management with state restoration.
- **GitHub Integration**: Authenticate with your GitHub account to view repositories, issues, PRs, and releases securely.
- **Modern UI**: Built with Jetpack Compose and Material 3 design principles, supporting dark and light themes.

## Installation
You can build the app from source or download the latest APK from the [Releases](https://github.com/sayanthrock/rockbrowser/releases) page.

## Build Instructions
Requirements:
- Android Studio Iguana or newer
- JDK 17
- Android SDK 34

1. Clone the repository:
   ```bash
   git clone https://github.com/sayanthrock/rockbrowser.git
   ```
2. Open the project in Android Studio.
3. Build the app using Gradle:
   ```bash
   ./gradlew assembleDebug
   ```

## Architecture
ROCK BROWSER follows the Modern Android Development (MAD) guidelines:
- **UI**: Jetpack Compose, Material 3
- **Architecture**: MVVM (Model-View-ViewModel)
- **Database**: Room for local storage (bookmarks, history)
- **Preferences**: DataStore for user settings
- **Asynchrony**: Kotlin Coroutines & Flow
- **Navigation**: Jetpack Navigation Compose

## GitHub Integration
GitHub integration is completely optional. If you choose to connect, ROCK BROWSER uses secure OAuth with PKCE. Tokens are securely stored in the Android Keystore. The app requests minimal permissions to display your profile, repositories, and notifications.

## Privacy & Security
We take privacy seriously:
- **No Telemetry**: We do not track your browsing habits.
- **Secure Storage**: Sensitive data like OAuth tokens are encrypted.
- **Minimal Permissions**: The app only asks for the permissions it absolutely needs to function.

## Supported Android Versions
- **Minimum**: Android 10 (API level 29)
- **Target**: Android 14 (API level 34)

## Contributing
Contributions are welcome! Please read `CONTRIBUTING.md` for guidelines on how to submit issues and pull requests.

## License
This project is licensed under the MIT License - see the `LICENSE` file for details.
