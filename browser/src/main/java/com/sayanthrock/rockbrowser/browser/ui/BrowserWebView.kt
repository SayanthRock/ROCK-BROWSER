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
