package com.yoenas.githubusers.ui.main.favorite

import android.app.Application
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoenas.githubusers.data.model.DetailUser
import com.yoenas.githubusers.data.repository.FavoriteRepository
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class FavoriteViewModel(application: Application) : ViewModel() {

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)

    val favoriteUsers: MutableLiveData<List<DetailUser>?> = MutableLiveData()

    fun getFavoriteUsers() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUser().collect {
                favoriteUsers.postValue(it)
            }
        }
    }
}