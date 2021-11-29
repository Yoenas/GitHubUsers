package com.yoenas.githubusers.data.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class DetailUser(
    @PrimaryKey
    @ColumnInfo(name = "login")
    @field:SerializedName("login")
    val login: String,

    @ColumnInfo(name = "company")
    @field:SerializedName("company")
    val company: String? = null,

    @ColumnInfo(name = "public_repos")
    @field:SerializedName("public_repos")
    val publicRepos: Int? = null,

    @ColumnInfo(name = "email")
    @field:SerializedName("email")
    val email: String? = null,

    @ColumnInfo(name = "followers")
    @field:SerializedName("followers")
    val followers: Int? = null,

    @ColumnInfo(name = "avatar_url")
    @field:SerializedName("avatar_url")
    val avatarUrl: String? = null,

    @ColumnInfo(name = "html_url")
    @field:SerializedName("html_url")
    val htmlUrl: String? = null,

    @ColumnInfo(name = "following")
    @field:SerializedName("following")
    val following: Int? = null,

    @ColumnInfo(name = "name")
    @field:SerializedName("name")
    val name: String? = null,

    @ColumnInfo(name = "location")
    @field:SerializedName("location")
    val location: String? = null,
)
