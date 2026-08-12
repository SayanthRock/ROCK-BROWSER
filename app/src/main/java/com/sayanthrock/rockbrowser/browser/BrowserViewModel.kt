package com.sayanthrock.rockbrowser.browser

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class BrowserViewModel : ViewModel() {

    private val _tabs = MutableStateFlow<List<Tab>>(listOf(Tab()))
    val tabs: StateFlow<List<Tab>> = _tabs.asStateFlow()

    private val _activeTabId = MutableStateFlow<String?>(_tabs.value.firstOrNull()?.id)
    val activeTabId: StateFlow<String?> = _activeTabId.asStateFlow()

    fun addNewTab(isPrivate: Boolean = false) {
        val newTab = Tab(isPrivate = isPrivate)
        _tabs.update { it + newTab }
        _activeTabId.value = newTab.id
    }

    fun closeTab(tabId: String) {
        _tabs.update { it.filter { tab -> tab.id != tabId } }
        if (_activeTabId.value == tabId) {
            _activeTabId.value = _tabs.value.lastOrNull()?.id
        }
        if (_tabs.value.isEmpty()) {
             addNewTab()
        }
    }

    fun selectTab(tabId: String) {
        if (_tabs.value.any { it.id == tabId }) {
            _activeTabId.value = tabId
            updateTab(tabId) { it.copy(lastAccessed = System.currentTimeMillis()) }
        }
    }

    fun updateTabProgress(tabId: String, progress: Int) {
        updateTab(tabId) { it.copy(progress = progress, isLoading = progress < 100) }
    }

    fun updateTabUrl(tabId: String, url: String) {
        updateTab(tabId) { it.copy(url = url) }
    }

    fun updateTabTitle(tabId: String, title: String) {
        updateTab(tabId) { it.copy(title = title) }
    }

    fun updateTabNavState(tabId: String, canGoBack: Boolean, canGoForward: Boolean) {
        updateTab(tabId) { it.copy(canGoBack = canGoBack, canGoForward = canGoForward) }
    }

    private fun updateTab(tabId: String, transform: (Tab) -> Tab) {
        _tabs.update { tabs ->
            tabs.map { if (it.id == tabId) transform(it) else it }
        }
    }

    fun getActiveTab(): Tab? {
        val id = _activeTabId.value
        return _tabs.value.find { it.id == id }
    }
}
