package com.yoenas.githubusers.ui.detail.following

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoenas.githubusers.data.model.User
import com.yoenas.githubusers.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowingViewModel : ViewModel() {

    private val following = MutableLiveData<ArrayList<User>>()

    fun getUserFollowing(username: String) {
        ApiConfig.getApiService().findUserFollowing(username)
            .enqueue(object : Callback<ArrayList<User>> {
                override fun onResponse(
                    call: Call<ArrayList<User>>,
                    response: Response<ArrayList<User>>
                ) {
                    if (response.isSuccessful) following.postValue(response.body())
                }

                override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                    Log.d("Failure ", t.message.toString())
                }

            })
    }

    fun getResultFollowing(): LiveData<ArrayList<User>> = following
}