package com.yoenas.githubusers.ui.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.yoenas.githubusers.data.model.GitHubUsers
import com.yoenas.githubusers.data.model.User
import com.yoenas.githubusers.data.repository.ThemeRepository
import com.yoenas.githubusers.network.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(application: Application) : ViewModel() {
    private val themeRepository: ThemeRepository = ThemeRepository(application)
    private val search = MutableLiveData<ArrayList<User>>()

    fun searchUser(searchQuery: String) {
        ApiConfig.getApiService().findUserBySearch(searchQuery)
            .enqueue(object : Callback<GitHubUsers> {
                override fun onResponse(call: Call<GitHubUsers>, response: Response<GitHubUsers>) {
                    if (response.isSuccessful) search.postValue(response.body()?.items)
                }

                override fun onFailure(call: Call<GitHubUsers>, t: Throwable) {
                    Log.d("Failure ", t.message.toString())
                }
            })
    }

    fun getResultSearchUser(): LiveData<ArrayList<User>> = search

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            themeRepository.saveThemeSetting(isDarkModeActive)
        }
    }
    fun getThemeSettings(): LiveData<Boolean> {
        return themeRepository.getThemeSetting().asLiveData()
    }
}