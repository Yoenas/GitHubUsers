package com.yoenas.githubusers.retrofit2

import com.yoenas.githubusers.data.DetailUser
import com.yoenas.githubusers.data.GitHubUsers
import com.yoenas.githubusers.data.User
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("search/users")
    @Headers("Authorization: token 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")
    fun findUserBySearch(@Query("q") searchQuery: String): Call<GitHubUsers>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun findUserDetailByUsername(@Path("username") userDetails: String): Call<DetailUser>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun findUserFollowers(@Path("username") userFollowers: String): Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun findUserFollowing(@Path("username") userFollowing: String): Call<ArrayList<User>>
}