package com.sayanthrock.rockbrowser.ui
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sayanthrock.rockbrowser.core.designsystem.RockBrowserTheme
import com.sayanthrock.rockbrowser.feature.tabs.TabManager
import com.sayanthrock.rockbrowser.feature.tabs.ui.TabScreen
import com.sayanthrock.rockbrowser.feature.history.ui.HistoryScreen
import com.sayanthrock.rockbrowser.feature.bookmarks.ui.BookmarksScreen
import com.sayanthrock.rockbrowser.feature.downloads.ui.DownloadsScreen
import com.sayanthrock.rockbrowser.feature.settings.ui.SettingsScreen
import com.sayanthrock.rockbrowser.feature.about.ui.AboutScreen
import com.sayanthrock.rockbrowser.feature.github.ui.GitHubConnectScreen
import com.sayanthrock.rockbrowser.feature.privatemode.ui.PrivateModeIntroScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BrowserApp() {
    val tabManager = remember { TabManager().apply { addNewTab() } }
    val navController = rememberNavController()

    RockBrowserTheme {
        NavHost(navController = navController, startDestination = "browser") {
            composable("browser") {
                val tabs by tabManager.tabs.collectAsState()
                val activeTabId by tabManager.activeTabId.collectAsState()
                val activeTab = tabs.find { it.id == activeTabId }
                var showMenu by remember { mutableStateOf(false) }

                if (showMenu) {
                    MenuBottomSheet(
                        onDismiss = { showMenu = false },
                        onNavigateTo = { route ->
                            if (route == "new_tab") {
                                tabManager.addNewTab()
                            } else {
                                navController.navigate(route)
                            }
                        }
                    )
                }

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
                                                               else if (urlInput.contains(".")) "https://$urlInput"
                                                               else "https://www.google.com/search?q=$urlInput"
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
            composable("history") { HistoryScreen() }
            composable("bookmarks") { BookmarksScreen() }
            composable("downloads") { DownloadsScreen() }
            composable("settings") { SettingsScreen() }
            composable("about") { AboutScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("github") { GitHubConnectScreen() }
            composable("new_private_tab") { PrivateModeIntroScreen() }
        }
    }
}
