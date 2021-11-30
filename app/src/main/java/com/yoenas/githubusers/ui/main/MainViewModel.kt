package com.yoenas.githubusers.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.yoenas.githubusers.data.repository.ThemeRepository
import kotlinx.coroutines.launch

class MainViewModel(application: Application) : ViewModel() {
    private val themeRepository: ThemeRepository = ThemeRepository(application)

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            themeRepository.saveThemeSetting(isDarkModeActive)
        }
    }

    fun getThemeSettings(): LiveData<Boolean> {
        return themeRepository.getThemeSetting().asLiveData()
    }
}