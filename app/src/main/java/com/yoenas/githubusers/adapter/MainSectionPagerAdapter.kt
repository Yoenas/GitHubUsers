package com.yoenas.githubusers.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yoenas.githubusers.ui.main.favorite.FavoriteFragment
import com.yoenas.githubusers.ui.main.search.SearchResultFragment

class MainSectionPagerAdapter(fa: FragmentActivity) :
    FragmentStateAdapter(fa) {
    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        return when (position) {
            0 -> SearchResultFragment()
            1 -> FavoriteFragment()
            else -> SearchResultFragment()
        }
    }
}