package com.yoenas.githubusers.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yoenas.githubusers.data.model.DetailUser
import com.yoenas.githubusers.data.repository.FavoriteRepository
import com.yoenas.githubusers.network.ApiConfig
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : ViewModel() {

    private val favoriteRepository: FavoriteRepository = FavoriteRepository(application)
    private val detail = MutableLiveData<DetailUser>()

    private val _isFavorite: MutableLiveData<Boolean> = MutableLiveData()
    val isFavorite get() = _isFavorite

    private val listFavoriteUsers: MutableLiveData<List<DetailUser>?> = MutableLiveData()

    init {
        getFavoriteUsers()
        _isFavorite.postValue(false)
    }

    fun showFavoriteUser(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            for (it in listFavoriteUsers.value ?: mutableListOf()) {
                if (favoriteUser?.login == it.login) {
                    _isFavorite.postValue(true)
                    break
                } else {
                    _isFavorite.postValue(false)
                }
            }
        }
    }

    fun checkFavoriteUser(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            if (_isFavorite.value == true) {
                delete(favoriteUser)
            } else {
                insert(favoriteUser)
            }
        }
    }

    private fun insert(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            if (favoriteUser != null) {
                favoriteRepository.insert(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(true)
            }
        }
    }

    private fun delete(favoriteUser: DetailUser?) {
        viewModelScope.launch {
            if (favoriteUser != null) {
                favoriteRepository.delete(favoriteUser)
                getFavoriteUsers()
                _isFavorite.postValue(false)
            }
        }
    }

    private fun getFavoriteUsers() {
        viewModelScope.launch {
            favoriteRepository.getFavoriteUser().collect {
                listFavoriteUsers.postValue(it)
            }
        }
    }

    fun getUser(username: String) {
        ApiConfig.getApiService().findUserDetailByUsername(username)
            .enqueue(object : Callback<DetailUser> {
                override fun onResponse(call: Call<DetailUser>, response: Response<DetailUser>) {
                    if (response.isSuccessful) detail.postValue(response.body())
                }

                override fun onFailure(call: Call<DetailUser>, t: Throwable) {
                    Log.d("Failure ", t.message.toString())
                }

            })
    }

    fun getUserDetails(): LiveData<DetailUser> = detail
}