package com.yoenas.githubusers.utils

import com.yoenas.githubusers.data.User

interface OnItemClickCallback {
    fun onItemClicked(user: User)
}