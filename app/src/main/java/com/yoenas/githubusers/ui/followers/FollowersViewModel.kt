package com.yoenas.githubusers.ui.followers

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yoenas.githubusers.data.User
import com.yoenas.githubusers.network.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowersViewModel : ViewModel() {

    private val followers = MutableLiveData<ArrayList<User>>()

    fun getUserFollower(username: String) {
        ApiConfig.getApiService().findUserFollowers(username).enqueue(object :
            Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (response.isSuccessful) followers.postValue(response.body())
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Log.d("Failure ", t.message.toString())
            }
        })
    }

    fun getResultFollowers(): LiveData<ArrayList<User>> = followers
}