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
