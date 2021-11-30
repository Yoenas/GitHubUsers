package com.yoenas.githubusers.data.repository

import android.app.Application
import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class ThemeRepository(context: Context) {
    private val Context.dataStore by preferencesDataStore("setting")
    private val key = booleanPreferencesKey("thene_setting")
    private val settingDataStore = context.dataStore

    fun getThemeSetting(): Flow<Boolean> {
        return settingDataStore.data.map { it ->
            it[key] ?: false
        }
    }

    suspend fun saveThemeSetting(isDarkMode: Boolean) {
        settingDataStore.edit { it ->
            it[key] = isDarkMode
        }
    }
}