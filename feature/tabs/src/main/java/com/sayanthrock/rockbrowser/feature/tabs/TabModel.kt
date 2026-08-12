package com.sayanthrock.rockbrowser.feature.tabs
import java.util.UUID
data class TabModel(val id: String = UUID.randomUUID().toString(), val url: String = "", val title: String = "New Tab")
