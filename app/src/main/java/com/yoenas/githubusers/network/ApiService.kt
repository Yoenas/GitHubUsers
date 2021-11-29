package com.yoenas.githubusers.network

import com.yoenas.githubusers.BuildConfig.API_KEY
import com.yoenas.githubusers.data.model.DetailUser
import com.yoenas.githubusers.data.model.GitHubUsers
import com.yoenas.githubusers.data.model.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token $API_KEY")
    fun findUserBySearch(@Query("q") searchQuery: String): Call<GitHubUsers>

    @GET("users/{username}")
    @Headers("Authorization: token $API_KEY")
    fun findUserDetailByUsername(@Path("username") userDetails: String): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token $API_KEY")
    fun findUserFollowers(@Path("username") userFollowers: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token $API_KEY")
    fun findUserFollowing(@Path("username") userFollowing: String): Call<ArrayList<User>>
}