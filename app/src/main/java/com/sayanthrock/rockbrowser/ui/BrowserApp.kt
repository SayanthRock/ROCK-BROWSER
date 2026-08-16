package com.sayanthrock.rockbrowser.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.sayanthrock.rockbrowser.core.designsystem.RockBrowserTheme
import com.sayanthrock.rockbrowser.feature.tabs.TabManager
import com.sayanthrock.rockbrowser.feature.tabs.ui.TabScreen
import com.sayanthrock.rockbrowser.feature.about.ui.AboutScreen
import com.sayanthrock.rockbrowser.feature.settings.ui.SettingsScreen
import com.sayanthrock.rockbrowser.feature.github.ui.GitHubConnectScreen
import com.sayanthrock.rockbrowser.feature.privatemode.ui.PrivateModeIntroScreen
import com.sayanthrock.rockbrowser.feature.bookmarks.ui.BookmarksScreen
import com.sayanthrock.rockbrowser.feature.history.ui.HistoryScreen
import com.sayanthrock.rockbrowser.feature.downloads.ui.DownloadsScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable fun BrowserApp() {
    val tabManager = remember { TabManager().apply { addNewTab() } }
    val tabs by tabManager.tabs.collectAsState()
    val activeTabId by tabManager.activeTabId.collectAsState()
    val activeTab = tabs.find { it.id == activeTabId }
    var showMenu by remember { mutableStateOf(false) }

    val navController = rememberNavController()

    if (showMenu) {
        MenuBottomSheet(
            onDismiss = { showMenu = false },
            onNavigateTo = { route ->
                if (route == "new_tab") {
                    tabManager.addNewTab()
                } else if (route == "share" || route == "find_in_page" || route == "desktop_site") {
                    // TODO: implement these browser actions
                } else if (route == "bookmarks" || route == "history" || route == "downloads" ||
                           route == "github" || route == "settings" || route == "about" ||
                           route == "new_private_tab") {
                    navController.navigate(route)
                } else {
                    // Ignore unknown routes
                }
            }
        )
    }

    RockBrowserTheme {
        NavHost(navController = navController, startDestination = "browser") {
            composable("browser") {
                Scaffold(
                    topBar = {
                        Surface(
                            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 8.dp),
                            shape = RoundedCornerShape(24.dp),
                            color = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.8f),
                            tonalElevation = 4.dp
                        ) {
                            var urlInput by remember(activeTab?.url) { mutableStateOf(activeTab?.url ?: "") }
                            TextField(
                                value = urlInput,
                                onValueChange = { urlInput = it },
                                modifier = Modifier.fillMaxWidth(),
                                placeholder = { Text("Search or enter address") },
                                singleLine = true,
                                colors = TextFieldDefaults.colors(
                                    focusedContainerColor = Color.Transparent,
                                    unfocusedContainerColor = Color.Transparent,
                                    focusedIndicatorColor = Color.Transparent,
                                    unfocusedIndicatorColor = Color.Transparent
                                ),
                                keyboardOptions = KeyboardOptions(imeAction = ImeAction.Go),
                                keyboardActions = KeyboardActions(onGo = {
                                    activeTab?.let { tab ->
                                        val finalUrl = if (urlInput.startsWith("http")) urlInput
                                                       else if (urlInput.contains(".")) "https://$urlInput"
                                                       else "https://www.google.com/search?q=$urlInput"
                                        tabManager.updateTab(tab.id) { it.copy(url = finalUrl) }
                                    }
                                }),
                                leadingIcon = {
                                    Icon(Icons.Default.Search, contentDescription = "Search")
                                }
                            )
                        }
                    },
                    bottomBar = {
                        BottomAppBar(
                            modifier = Modifier.clip(RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp)),
                            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.9f),
                            tonalElevation = 8.dp
                        ) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceEvenly,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                IconButton(onClick = { activeTab?.let { tabManager.updateTab(it.id) { t -> t.copy(url = "") } } }) {
                                    Icon(Icons.Default.Home, contentDescription = "Home")
                                }
                                Button(
                                    onClick = { tabManager.addNewTab() },
                                    shape = RoundedCornerShape(16.dp),
                                    colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer, contentColor = MaterialTheme.colorScheme.onPrimaryContainer)
                                ) {
                                    Text("${tabs.size}")
                                }
                                IconButton(onClick = { showMenu = true }) {
                                    Icon(Icons.Default.Menu, contentDescription = "Menu")
                                }
                            }
                        }
                    },
                    containerColor = MaterialTheme.colorScheme.background
                ) { paddingValues ->
                    Box(modifier = Modifier.fillMaxSize().padding(paddingValues)) {
                        if (activeTab != null) {
                            TabScreen(tab = activeTab)
                        } else {
                            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                                Text("No active tab", style = MaterialTheme.typography.titleLarge)
                            }
                        }
                    }
                }
            }
            composable("settings") { SettingsScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("about") { AboutScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("github") { GitHubConnectScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("bookmarks") { BookmarksScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("history") { HistoryScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("downloads") { DownloadsScreen(onNavigateBack = { navController.popBackStack() }) }
            composable("new_private_tab") { PrivateModeIntroScreen(onNavigateBack = { navController.popBackStack() }) }
        }
    }
}
