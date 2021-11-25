package com.yoenas.githubusers.data

import com.google.gson.annotations.SerializedName

data class DetailUser(

    @field:SerializedName("repos_url")
    val reposUrl: String? = null,

    @field:SerializedName("twitter_username")
    val twitterUsername: Any? = null,

    @field:SerializedName("bio")
    val bio: Any? = null,

    @field:SerializedName("login")
    val login: String? = null,

    @field:SerializedName("blog")
    val blog: String? = null,

    @field:SerializedName("company")
    val company: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @field:SerializedName("email")
    val email: Any? = null,

    @field:SerializedName("followers")
    val followers: Int? = null,

    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @field:SerializedName("following")
    val following: Int? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("location")
    val location: String? = null,
)
