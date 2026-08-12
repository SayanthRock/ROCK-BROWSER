#!/bin/bash
set -e

# Core Design System
mkdir -p core/designsystem/src/main/java/com/sayanthrock/rockbrowser/core/designsystem
cat << 'KT' > core/designsystem/src/main/java/com/sayanthrock/rockbrowser/core/designsystem/Color.kt
package com.sayanthrock.rockbrowser.core.designsystem
import androidx.compose.ui.graphics.Color
val Black = Color(0xFF000000)
val Charcoal = Color(0xFF1E1E1E)
val DeepGray = Color(0xFF2D2D2D)
val WarmGray = Color(0xFFF5F5F5)
val LightGray = Color(0xFFE0E0E0)
val White = Color(0xFFFFFFFF)
val SubtleMetallic = Color(0xFF8B9BB4)
val SubtleAccent = Color(0xFF5D7BA6)
KT

cat << 'KT' > core/designsystem/src/main/java/com/sayanthrock/rockbrowser/core/designsystem/Typography.kt
package com.sayanthrock.rockbrowser.core.designsystem
import androidx.compose.material3.Typography
val Typography = Typography()
KT

cat << 'KT' > core/designsystem/src/main/java/com/sayanthrock/rockbrowser/core/designsystem/Shape.kt
package com.sayanthrock.rockbrowser.core.designsystem
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp
val Shapes = Shapes(small = RoundedCornerShape(12.dp), medium = RoundedCornerShape(16.dp), large = RoundedCornerShape(20.dp), extraLarge = RoundedCornerShape(28.dp))
KT

cat << 'KT' > core/designsystem/src/main/java/com/sayanthrock/rockbrowser/core/designsystem/Theme.kt
package com.sayanthrock.rockbrowser.core.designsystem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
private val DarkColorScheme = darkColorScheme(primary = SubtleMetallic, background = Black)
private val LightColorScheme = lightColorScheme(primary = SubtleAccent, background = White)
@Composable fun RockBrowserTheme(darkTheme: Boolean = true, content: @Composable () -> Unit) {
    MaterialTheme(colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme, typography = Typography, shapes = Shapes, content = content)
}
KT

# Browser Module
mkdir -p browser/src/main/java/com/sayanthrock/rockbrowser/browser/engine browser/src/main/java/com/sayanthrock/rockbrowser/browser/ui
cat << 'KT' > browser/src/main/java/com/sayanthrock/rockbrowser/browser/engine/RockWebViewClient.kt
package com.sayanthrock.rockbrowser.browser.engine
import android.webkit.WebViewClient
class RockWebViewClient : WebViewClient()
KT

cat << 'KT' > browser/src/main/java/com/sayanthrock/rockbrowser/browser/engine/RockWebChromeClient.kt
package com.sayanthrock.rockbrowser.browser.engine
import android.webkit.WebChromeClient
class RockWebChromeClient : WebChromeClient()
KT

cat << 'KT' > browser/src/main/java/com/sayanthrock/rockbrowser/browser/ui/BrowserWebView.kt
package com.sayanthrock.rockbrowser.browser.ui
import android.view.ViewGroup
import android.webkit.WebView
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sayanthrock.rockbrowser.browser.engine.RockWebChromeClient
import com.sayanthrock.rockbrowser.browser.engine.RockWebViewClient
@Composable fun BrowserWebView(url: String, modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier, factory = { context -> WebView(context).apply { layoutParams = ViewGroup.LayoutParams(-1, -1); webViewClient = RockWebViewClient(); webChromeClient = RockWebChromeClient(); if (url.isNotEmpty()) loadUrl(url) } }, update = { if (url.isNotEmpty() && it.url != url) it.loadUrl(url) })
}
KT

cat << 'KTS' > core/designsystem/build.gradle.kts
plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }
android { namespace = "com.sayanthrock.rockbrowser.core.designsystem"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" }; buildFeatures { compose = true }; composeOptions { kotlinCompilerExtensionVersion = "1.5.1" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.appcompat:appcompat:1.6.1"); implementation("com.google.android.material:material:1.11.0"); implementation(platform("androidx.compose:compose-bom:2024.02.00")); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.ui:ui-graphics"); implementation("androidx.compose.material3:material3") }
KTS

cat << 'KTS' > browser/build.gradle.kts
plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }
android { namespace = "com.sayanthrock.rockbrowser.browser"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" }; buildFeatures { compose = true }; composeOptions { kotlinCompilerExtensionVersion = "1.5.1" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.webkit:webkit:1.10.0"); implementation(platform("androidx.compose:compose-bom:2024.02.00")); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.material3:material3") }
KTS

# Setup Tabs Feature
mkdir -p feature/tabs/src/main/java/com/sayanthrock/rockbrowser/feature/tabs/ui
cat << 'KT' > feature/tabs/src/main/java/com/sayanthrock/rockbrowser/feature/tabs/TabModel.kt
package com.sayanthrock.rockbrowser.feature.tabs
import java.util.UUID
data class TabModel(val id: String = UUID.randomUUID().toString(), val url: String = "", val title: String = "New Tab")
KT

cat << 'KT' > feature/tabs/src/main/java/com/sayanthrock/rockbrowser/feature/tabs/TabManager.kt
package com.sayanthrock.rockbrowser.feature.tabs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class TabManager {
    private val _tabs = MutableStateFlow<List<TabModel>>(emptyList())
    val tabs: StateFlow<List<TabModel>> = _tabs.asStateFlow()
    private val _activeTabId = MutableStateFlow<String?>(null)
    val activeTabId: StateFlow<String?> = _activeTabId.asStateFlow()
    fun addNewTab(): String { val newTab = TabModel(); _tabs.update { it + newTab }; _activeTabId.value = newTab.id; return newTab.id }
    fun updateTab(tabId: String, update: (TabModel) -> TabModel) { _tabs.update { tabs -> tabs.map { if (it.id == tabId) update(it) else it } } }
}
KT

cat << 'KT' > feature/tabs/src/main/java/com/sayanthrock/rockbrowser/feature/tabs/ui/TabScreen.kt
package com.sayanthrock.rockbrowser.feature.tabs.ui
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.sayanthrock.rockbrowser.feature.tabs.TabModel
import com.sayanthrock.rockbrowser.browser.ui.BrowserWebView
@Composable fun TabScreen(tab: TabModel, modifier: Modifier = Modifier) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) { if (tab.url.isEmpty()) Text("New Tab Page") else BrowserWebView(url = tab.url) }
}
KT

cat << 'KTS' > feature/tabs/build.gradle.kts
plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }
android { namespace = "com.sayanthrock.rockbrowser.feature.tabs"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" }; buildFeatures { compose = true }; composeOptions { kotlinCompilerExtensionVersion = "1.5.1" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation(project(":browser")); implementation(project(":core:designsystem")); implementation(platform("androidx.compose:compose-bom:2024.02.00")); implementation("androidx.compose.ui:ui"); implementation("androidx.compose.material3:material3") }
KTS

# Setup Main UI
cat << 'KT' > app/src/main/java/com/sayanthrock/rockbrowser/ui/BrowserApp.kt
package com.sayanthrock.rockbrowser.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.sayanthrock.rockbrowser.core.designsystem.RockBrowserTheme
import com.sayanthrock.rockbrowser.feature.tabs.TabManager
import com.sayanthrock.rockbrowser.feature.tabs.ui.TabScreen
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BrowserApp() {
    val tabManager = remember { TabManager().apply { addNewTab() } }
    val tabs by tabManager.tabs.collectAsState()
    val activeTabId by tabManager.activeTabId.collectAsState()
    val activeTab = tabs.find { it.id == activeTabId }
    var showMenu by remember { mutableStateOf(false) }

    if (showMenu) {
        MenuBottomSheet(
            onDismiss = { showMenu = false },
            onNavigateTo = { /* Handle navigation here */ }
        )
    }

    RockBrowserTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        var urlInput by remember(activeTab?.url) { mutableStateOf(activeTab?.url ?: "") }
                        TextField(
                            value = urlInput,
                            onValueChange = { urlInput = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("Search or enter website address") },
                            singleLine = true,
                            trailingIcon = {
                                Button(onClick = {
                                    activeTab?.let { tab ->
                                        val finalUrl = if (urlInput.startsWith("http")) urlInput
                                                       else if (urlInput.contains(".")) "https://\$urlInput"
                                                       else "https://www.google.com/search?q=\$urlInput"
                                        tabManager.updateTab(tab.id) { it.copy(url = finalUrl) }
                                    }
                                }) { Text("Go") }
                            }
                        )
                    }
                )
            },
            bottomBar = {
                BottomAppBar {
                    Row(modifier = Modifier.fillMaxWidth()) {
                        Button(onClick = { activeTab?.let { tabManager.updateTab(it.id) { t -> t.copy(url = "") } } }) { Text("Home") }
                        Button(onClick = { tabManager.addNewTab() }) { Text("Tabs (\${tabs.size})") }
                        Button(onClick = { showMenu = true }) { Text("Menu") }
                    }
                }
            }
        ) { paddingValues ->
            Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                if (activeTab != null) TabScreen(tab = activeTab) else Text("No active tab")
            }
        }
    }
}
KT

cat << 'KT' > app/src/main/java/com/sayanthrock/rockbrowser/MainActivity.kt
package com.sayanthrock.rockbrowser
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.sayanthrock.rockbrowser.ui.BrowserApp
class MainActivity : ComponentActivity() { override fun onCreate(savedInstanceState: Bundle?) { super.onCreate(savedInstanceState); setContent { BrowserApp() } } }
KT

# Setup feature UI placeholders
mkdir -p feature/history/src/main/java/com/sayanthrock/rockbrowser/feature/history/ui
cat << 'KT' > feature/history/src/main/java/com/sayanthrock/rockbrowser/feature/history/ui/HistoryScreen.kt
package com.sayanthrock.rockbrowser.feature.history.ui
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable fun HistoryScreen() { Text("History") }
KT

mkdir -p feature/bookmarks/src/main/java/com/sayanthrock/rockbrowser/feature/bookmarks/ui
cat << 'KT' > feature/bookmarks/src/main/java/com/sayanthrock/rockbrowser/feature/bookmarks/ui/BookmarksScreen.kt
package com.sayanthrock.rockbrowser.feature.bookmarks.ui
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable fun BookmarksScreen() { Text("Bookmarks") }
KT

mkdir -p feature/downloads/src/main/java/com/sayanthrock/rockbrowser/feature/downloads/ui
cat << 'KT' > feature/downloads/src/main/java/com/sayanthrock/rockbrowser/feature/downloads/ui/DownloadsScreen.kt
package com.sayanthrock.rockbrowser.feature.downloads.ui
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable fun DownloadsScreen() { Text("Downloads") }
KT

mkdir -p feature/settings/src/main/java/com/sayanthrock/rockbrowser/feature/settings/ui
cat << 'KT' > feature/settings/src/main/java/com/sayanthrock/rockbrowser/feature/settings/ui/SettingsScreen.kt
package com.sayanthrock.rockbrowser.feature.settings.ui
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable fun SettingsScreen() { Text("Settings") }
KT

mkdir -p feature/github/src/main/java/com/sayanthrock/rockbrowser/feature/github/ui
cat << 'KT' > feature/github/src/main/java/com/sayanthrock/rockbrowser/feature/github/ui/GitHubConnectScreen.kt
package com.sayanthrock.rockbrowser.feature.github.ui
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
@Composable fun GitHubConnectScreen() { Text("GitHub") }
KT

# BottomSheet and remaining screens
cat << 'KT' > app/src/main/java/com/sayanthrock/rockbrowser/ui/MenuBottomSheet.kt
package com.sayanthrock.rockbrowser.ui
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun MenuBottomSheet(onDismiss: () -> Unit, onNavigateTo: (String) -> Unit) {
    ModalBottomSheet(onDismissRequest = onDismiss) {
        LazyColumn(modifier = Modifier.padding(bottom = 32.dp)) {
            item { Text("Menu", style = MaterialTheme.typography.titleMedium, modifier = Modifier.padding(16.dp)) }
            item { MenuItem("New tab") { onDismiss(); onNavigateTo("new_tab") } }
            item { MenuItem("New private tab") { onDismiss(); onNavigateTo("new_private_tab") } }
            item { MenuItem("Bookmarks") { onDismiss(); onNavigateTo("bookmarks") } }
            item { MenuItem("History") { onDismiss(); onNavigateTo("history") } }
            item { MenuItem("Downloads") { onDismiss(); onNavigateTo("downloads") } }
            item { HorizontalDivider() }
            item { MenuItem("Share") { onDismiss(); onNavigateTo("share") } }
            item { MenuItem("Find in page") { onDismiss(); onNavigateTo("find_in_page") } }
            item { MenuItem("Desktop site") { onDismiss(); onNavigateTo("desktop_site") } }
            item { HorizontalDivider() }
            item { MenuItem("GitHub") { onDismiss(); onNavigateTo("github") } }
            item { MenuItem("Settings") { onDismiss(); onNavigateTo("settings") } }
            item { MenuItem("About") { onDismiss(); onNavigateTo("about") } }
        }
    }
}
@Composable fun MenuItem(text: String, onClick: () -> Unit) {
    Text(text = text, modifier = Modifier.fillMaxWidth().clickable(onClick = onClick).padding(16.dp), style = MaterialTheme.typography.bodyLarge)
}
KT

mkdir -p feature/about/src/main/java/com/sayanthrock/rockbrowser/feature/about/ui
cat << 'KT' > feature/about/src/main/java/com/sayanthrock/rockbrowser/feature/about/ui/AboutScreen.kt
package com.sayanthrock.rockbrowser.feature.about.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.sayanthrock.rockbrowser.core.designsystem.SubtleMetallic
@OptIn(ExperimentalMaterial3Api::class)
@Composable fun AboutScreen(onNavigateBack: () -> Unit) {
    Scaffold(topBar = { TopAppBar(title = { Text("About") }) }) { paddingValues ->
        Column(modifier = Modifier.fillMaxSize().padding(paddingValues).padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
            Text(text = "ROCK BROWSER", style = MaterialTheme.typography.titleLarge, color = SubtleMetallic)
            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Version 1.0.0", style = MaterialTheme.typography.bodyLarge)
            Spacer(modifier = Modifier.height(24.dp))
            Text(text = "Fast browsing. Private controls. ROCK design. Optional GitHub integration.", style = MaterialTheme.typography.bodyMedium, textAlign = TextAlign.Center)
        }
    }
}
KT

mkdir -p feature/private/src/main/java/com/sayanthrock/rockbrowser/feature/privatemode/ui
cat << 'KT' > feature/private/src/main/java/com/sayanthrock/rockbrowser/feature/privatemode/ui/PrivateModeScreen.kt
package com.sayanthrock.rockbrowser.feature.privatemode.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
@Composable fun PrivateModeIntroScreen() {
    Column(modifier = Modifier.fillMaxSize().padding(24.dp), verticalArrangement = Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
        Text(text = "ROCK PRIVATE", style = MaterialTheme.typography.titleLarge, color = MaterialTheme.colorScheme.primary)
        Spacer(modifier = Modifier.height(16.dp))
        Text(text = "Private browsing prevents ROCK BROWSER from saving normal local browsing history.\n\nIt does not make you anonymous online.", style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
    }
}
KT

mkdir -p core/datastore/src/main/java/com/sayanthrock/rockbrowser/core/datastore
cat << 'KTS' > core/datastore/build.gradle.kts
plugins { id("com.android.library"); id("org.jetbrains.kotlin.android") }
android { namespace = "com.sayanthrock.rockbrowser.core.datastore"; compileSdk = 34; defaultConfig { minSdk = 29 }; compileOptions { sourceCompatibility = JavaVersion.VERSION_17; targetCompatibility = JavaVersion.VERSION_17 }; kotlinOptions { jvmTarget = "17" } }
dependencies { implementation("androidx.core:core-ktx:1.12.0"); implementation("androidx.datastore:datastore-preferences:1.0.0") }
KTS

cat << 'KT' > core/datastore/src/main/java/com/sayanthrock/rockbrowser/core/datastore/RockPreferences.kt
package com.sayanthrock.rockbrowser.core.datastore
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
val Context.dataStore by preferencesDataStore(name = "rock_settings")
class RockPreferences(private val context: Context) {
    companion object {
        val THEME_MODE = intPreferencesKey("theme_mode")
        val SEARCH_ENGINE = stringPreferencesKey("search_engine")
        val JAVASCRIPT_ENABLED = booleanPreferencesKey("javascript_enabled")
        val COOKIES_ENABLED = booleanPreferencesKey("cookies_enabled")
        val DO_NOT_TRACK = booleanPreferencesKey("do_not_track")
    }
    val themeMode: Flow<Int> = context.dataStore.data.map { it[THEME_MODE] ?: 0 }
    val searchEngine: Flow<String> = context.dataStore.data.map { it[SEARCH_ENGINE] ?: "google" }
    val javascriptEnabled: Flow<Boolean> = context.dataStore.data.map { it[JAVASCRIPT_ENABLED] ?: true }
    suspend fun setThemeMode(mode: Int) { context.dataStore.edit { it[THEME_MODE] = mode } }
    suspend fun setSearchEngine(engine: String) { context.dataStore.edit { it[SEARCH_ENGINE] = engine } }
}
KT
