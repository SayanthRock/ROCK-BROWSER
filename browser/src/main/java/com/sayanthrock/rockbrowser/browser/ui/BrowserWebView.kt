package com.sayanthrock.rockbrowser.browser.ui
import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.view.ViewGroup
import android.webkit.CookieManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.sayanthrock.rockbrowser.browser.engine.RockWebChromeClient
import com.sayanthrock.rockbrowser.browser.engine.RockWebViewClient

@SuppressLint("SetJavaScriptEnabled")
@Composable fun BrowserWebView(url: String, modifier: Modifier = Modifier) {
    AndroidView(modifier = modifier, factory = { context ->
        WebView(context).apply {
            layoutParams = ViewGroup.LayoutParams(-1, -1)
            webViewClient = RockWebViewClient()
            webChromeClient = RockWebChromeClient()

            settings.apply {
                javaScriptEnabled = true
                domStorageEnabled = true
                databaseEnabled = true
                setSupportZoom(true)
                builtInZoomControls = true
                displayZoomControls = false
                useWideViewPort = true
                loadWithOverviewMode = true
                javaScriptCanOpenWindowsAutomatically = true
            }

            CookieManager.getInstance().setAcceptCookie(true)
            CookieManager.getInstance().setAcceptThirdPartyCookies(this, true)

            if (url.isNotEmpty()) loadUrl(url)
        }
    }, update = {
        if (url.isNotEmpty() && it.url != url) it.loadUrl(url)
    })
}
