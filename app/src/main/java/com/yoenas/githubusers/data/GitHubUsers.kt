package com.yoenas.githubusers.data

import com.google.gson.annotations.SerializedName

data class GitHubUsers(
    @field:SerializedName("items")
    val items: ArrayList<User>
)