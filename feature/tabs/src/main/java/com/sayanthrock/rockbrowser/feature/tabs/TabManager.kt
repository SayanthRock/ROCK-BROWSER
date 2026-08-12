package com.sayanthrock.rockbrowser.feature.tabs
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
class TabManager {
    private val _tabs = MutableStateFlow<List<TabModel>>(emptyList())
    val tabs: StateFlow<List<TabModel>> = _tabs.asStateFlow()
    private val _activeTabId = MutableStateFlow<String?>(null)
    val activeTabId: StateFlow<String?> = _activeTabId.asStateFlow()
    fun addNewTab(): String { val newTab = TabModel(); _tabs.update { it + newTab }; _activeTabId.value = newTab.id; return newTab.id }
    fun updateTab(tabId: String, update: (TabModel) -> TabModel) { _tabs.update { tabs -> tabs.map { if (it.id == tabId) update(it) else it } } }
}
