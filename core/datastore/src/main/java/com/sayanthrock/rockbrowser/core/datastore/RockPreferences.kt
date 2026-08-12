package com.sayanthrock.rockbrowser.core.datastore
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
val Context.dataStore by preferencesDataStore(name = "rock_settings")
class RockPreferences(private val context: Context) {
    companion object {
        val THEME_MODE = intPreferencesKey("theme_mode")
        val SEARCH_ENGINE = stringPreferencesKey("search_engine")
        val JAVASCRIPT_ENABLED = booleanPreferencesKey("javascript_enabled")
        val COOKIES_ENABLED = booleanPreferencesKey("cookies_enabled")
        val DO_NOT_TRACK = booleanPreferencesKey("do_not_track")
    }
    val themeMode: Flow<Int> = context.dataStore.data.map { it[THEME_MODE] ?: 0 }
    val searchEngine: Flow<String> = context.dataStore.data.map { it[SEARCH_ENGINE] ?: "google" }
    val javascriptEnabled: Flow<Boolean> = context.dataStore.data.map { it[JAVASCRIPT_ENABLED] ?: true }
    suspend fun setThemeMode(mode: Int) { context.dataStore.edit { it[THEME_MODE] = mode } }
    suspend fun setSearchEngine(engine: String) { context.dataStore.edit { it[SEARCH_ENGINE] = engine } }
}
