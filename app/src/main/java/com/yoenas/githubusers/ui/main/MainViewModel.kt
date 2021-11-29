package com.yoenas.githubusers.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoenas.githubusers.data.GitHubUsers
import com.yoenas.githubusers.data.User
import com.yoenas.githubusers.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel : ViewModel() {

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
}