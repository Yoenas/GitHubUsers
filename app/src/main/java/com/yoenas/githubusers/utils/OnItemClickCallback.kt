package com.yoenas.githubusers.utils

import com.yoenas.githubusers.data.model.User

interface OnItemClickCallback {
    fun onItemClicked(user: User)
}