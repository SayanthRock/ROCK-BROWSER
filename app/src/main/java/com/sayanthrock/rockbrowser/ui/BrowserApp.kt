package com.sayanthrock.rockbrowser.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.viewmodel.compose.viewModel
import com.sayanthrock.rockbrowser.core.designsystem.RockBrowserTheme
import com.sayanthrock.rockbrowser.feature.tabs.TabManager
import com.sayanthrock.rockbrowser.feature.tabs.ui.TabScreen
import com.sayanthrock.rockbrowser.feature.settings.ui.SettingsScreen
import com.sayanthrock.rockbrowser.feature.history.ui.HistoryScreen
import com.sayanthrock.rockbrowser.feature.downloads.ui.DownloadsScreen
import com.sayanthrock.rockbrowser.feature.bookmarks.ui.BookmarksScreen
import com.sayanthrock.rockbrowser.feature.privatemode.ui.PrivateModeIntroScreen
import com.sayanthrock.rockbrowser.feature.about.ui.AboutScreen
import com.sayanthrock.rockbrowser.feature.github.ui.GitHubConnectScreen
import androidx.lifecycle.ViewModel

class BrowserViewModel : ViewModel() {
    val tabManager = TabManager()
    init {
        tabManager.addNewTab()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BrowserApp() {
    val navController = rememberNavController()

    RockBrowserTheme {
        NavHost(navController = navController, startDestination = "browser") {
            composable("browser") {
                val viewModel: BrowserViewModel = viewModel()
                val tabManager = viewModel.tabManager
                val tabs by tabManager.tabs.collectAsState()
                val activeTabId by tabManager.activeTabId.collectAsState()
                val activeTab = tabs.find { it.id == activeTabId }
                var showMenu by rememberSaveable { mutableStateOf(false) }

                if (showMenu) {
                    MenuBottomSheet(
                        onDismiss = { showMenu = false },
                        onNavigateTo = { route ->
                            showMenu = false
                            when (route) {
                                "new_tab" -> tabManager.addNewTab()
                                "new_private_tab" -> navController.navigate("private")
                                "bookmarks" -> navController.navigate("bookmarks")
                                "history" -> navController.navigate("history")
                                "downloads" -> navController.navigate("downloads")
                                "github" -> navController.navigate("github")
                                "settings" -> navController.navigate("settings")
                                "about" -> navController.navigate("about")
                                "share", "find_in_page", "desktop_site" -> {
                                    // Features not implemented yet
                                }
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
            composable("settings") { SettingsScreen() }
            composable("history") { HistoryScreen() }
            composable("downloads") { DownloadsScreen() }
            composable("bookmarks") { BookmarksScreen() }
            composable("private") { PrivateModeIntroScreen() }
            composable("about") { AboutScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("github") { GitHubConnectScreen() }
        }
    }
}
