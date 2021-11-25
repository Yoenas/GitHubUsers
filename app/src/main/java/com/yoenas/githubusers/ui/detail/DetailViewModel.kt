package com.yoenas.githubusers.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoenas.githubusers.data.DetailUser
import com.yoenas.githubusers.retrofit2.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel : ViewModel() {

    private val detail = MutableLiveData<DetailUser>()

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