package com.yoenas.githubusers.data.repository

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ThemeRepository @Inject constructor(@ApplicationContext context: Context) {
    private val Context.dataStore by preferencesDataStore("setting")
    private val key = booleanPreferencesKey("theme_settings")
    private val settingDataStore = context.dataStore

    fun getThemeSetting(): Flow<Boolean> {
        return settingDataStore.data.map {
            it[key] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        settingDataStore.edit {
            it[key] = isDarkMode
        }
    }
}