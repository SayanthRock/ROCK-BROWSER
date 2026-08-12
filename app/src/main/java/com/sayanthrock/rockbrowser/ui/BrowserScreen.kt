package com.sayanthrock.rockbrowser.ui

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.sayanthrock.rockbrowser.browser.BrowserViewModel

@SuppressLint("SetJavaScriptEnabled")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BrowserScreen(
    navController: NavController,
    viewModel: BrowserViewModel = viewModel()
) {
    val tabs by viewModel.tabs.collectAsState()
    val activeTabId by viewModel.activeTabId.collectAsState()
    val activeTab = tabs.find { it.id == activeTabId }




    var webView: WebView? by remember { mutableStateOf(null) }
    var urlInput by remember { mutableStateOf(activeTab?.url ?: "") }

    LaunchedEffect(activeTab?.url) {
        if (activeTab?.url != null && activeTab.url != webView?.url) {
             urlInput = activeTab.url
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    OutlinedTextField(
                        value = urlInput,
                        onValueChange = { urlInput = it },
                        modifier = Modifier.fillMaxWidth().padding(end = 8.dp),
                        singleLine = true,
                        keyboardOptions = KeyboardOptions(
                            keyboardType = KeyboardType.Uri,
                            imeAction = ImeAction.Go
                        ),
                        keyboardActions = KeyboardActions(
                            onGo = {
                                var searchUrl = urlInput
                                if (!searchUrl.startsWith("http://") && !searchUrl.startsWith("https://")) {
                                    searchUrl = if (searchUrl.contains(".") && !searchUrl.contains(" ")) {
                                        "https://$searchUrl"
                                    } else {
                                        "https://www.google.com/search?q=$searchUrl"
                                    }
                                }
                                webView?.loadUrl(searchUrl)
                            }
                        )
                    )
                },
                actions = {
                    IconButton(onClick = { navController.navigate("settings") }) {
                        Icon(Icons.Filled.MoreVert, contentDescription = "Settings")
                    }
                }
            )
        },
        bottomBar = {
            BottomAppBar {
                IconButton(
                    onClick = { webView?.goBack() },
                    enabled = activeTab?.canGoBack == true
                ) {
                    Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                }
                IconButton(
                    onClick = { webView?.goForward() },
                    enabled = activeTab?.canGoForward == true
                ) {
                    Icon(Icons.Filled.ArrowForward, contentDescription = "Forward")
                }
                IconButton(
                    onClick = { webView?.reload() }
                ) {
                    Icon(Icons.Filled.Refresh, contentDescription = "Reload")
                }
                Spacer(Modifier.weight(1f))
                IconButton(onClick = { webView?.loadUrl("https://www.google.com") }) {
                     Icon(Icons.Filled.Home, contentDescription = "Home")
                }
                IconButton(onClick = { navController.navigate("tabs") }) {
                    Icon(Icons.Filled.List, contentDescription = "Tabs")
                }
            }
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            if (activeTab?.isLoading == true) {
                LinearProgressIndicator(
                    progress = activeTab.progress / 100f,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            AndroidView(
                factory = { ctx ->
                    WebView(ctx).apply {
                        settings.javaScriptEnabled = true
                        settings.domStorageEnabled = true
                        webViewClient = object : WebViewClient() {
                            override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
                                return false
                            }

                            override fun onPageStarted(view: WebView?, url: String?, favicon: Bitmap?) {
                                super.onPageStarted(view, url, favicon)
                                url?.let {
                                    urlInput = it
                                    activeTabId?.let { id -> viewModel.updateTabUrl(id, it) }
                                }
                            }

                            override fun onPageFinished(view: WebView?, url: String?) {
                                super.onPageFinished(view, url)
                                activeTabId?.let { id ->
                                    viewModel.updateTabNavState(id, view?.canGoBack() ?: false, view?.canGoForward() ?: false)
                                    view?.title?.let { title -> viewModel.updateTabTitle(id, title) }
                                }
                            }
                        }
                        webChromeClient = object : WebChromeClient() {
                            override fun onProgressChanged(view: WebView?, newProgress: Int) {
                                super.onProgressChanged(view, newProgress)
                                activeTabId?.let { id -> viewModel.updateTabProgress(id, newProgress) }
                            }
                        }
                        loadUrl(activeTab?.url ?: "https://www.google.com")
                        webView = this
                    }
                },
                update = { view ->
                    webView = view
                },
                modifier = Modifier.fillMaxSize()
            )
        }
    }
}
