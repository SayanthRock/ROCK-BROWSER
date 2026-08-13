package com.sayanthrock.rockbrowser.browser.engine

import android.content.Intent
import android.net.Uri
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient

class RockWebViewClient : WebViewClient() {
    override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
        val url = request?.url?.toString() ?: return false
        val uri = Uri.parse(url)

        if (url.startsWith("http://") || url.startsWith("https://")) {
            // Let the WebView load standard web URLs
            return false
        } else if (url.startsWith("intent://")) {
            try {
                val intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME)
                if (intent != null) {
                    val fallbackUrl = intent.getStringExtra("browser_fallback_url")
                    if (fallbackUrl != null) {
                        view?.loadUrl(fallbackUrl)
                        return true
                    }
                    val context = view?.context
                    if (context != null) {
                        intent.addCategory(Intent.CATEGORY_BROWSABLE)
                        intent.component = null
                        intent.selector = null
                        if (intent.resolveActivity(context.packageManager) != null) {
                            context.startActivity(intent)
                            return true
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return true
        } else {
            // Handle other custom schemes (like mailto:, tel:, etc., and github sign in redirects)
            try {
                val context = view?.context
                if (context != null) {
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    if (intent.resolveActivity(context.packageManager) != null) {
                        context.startActivity(intent)
                        return true
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }

        return false
    }
}
