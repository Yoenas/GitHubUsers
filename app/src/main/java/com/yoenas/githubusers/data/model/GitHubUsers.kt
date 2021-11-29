package com.yoenas.githubusers.data.model

import com.google.gson.annotations.SerializedName

data class GitHubUsers(
    @field:SerializedName("items")
    val items: ArrayList<User>
)