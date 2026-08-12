package com.sayanthrock.rockbrowser.browser

import java.util.UUID

data class Tab(
    val id: String = UUID.randomUUID().toString(),
    val url: String = "https://www.google.com",
    val title: String = "New Tab",
    val isPrivate: Boolean = false,
    val isLoading: Boolean = false,
    val progress: Int = 0,
    val canGoBack: Boolean = false,
    val canGoForward: Boolean = false,
    val lastAccessed: Long = System.currentTimeMillis()
)
