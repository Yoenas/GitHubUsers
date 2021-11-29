package com.yoenas.githubusers.adapter

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.yoenas.githubusers.ui.detail.followers.FollowersFragment
import com.yoenas.githubusers.ui.detail.following.FollowingFragment

class SectionPagerAdapter(fa: FragmentActivity, bundle: Bundle) :
    FragmentStateAdapter(fa) {

    private var mBundle: Bundle = bundle

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {
        var fragment: Fragment? = null
        when (position) {
            0 -> fragment = FollowersFragment()
            1 -> fragment = FollowingFragment()
        }
        fragment?.arguments = this.mBundle
        return fragment as Fragment
    }
}