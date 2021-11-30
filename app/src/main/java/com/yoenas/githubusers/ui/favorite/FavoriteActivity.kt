package com.yoenas.githubusers.ui.favorite

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.adapter.UserAdapter
import com.yoenas.githubusers.data.model.DetailUser
import com.yoenas.githubusers.data.model.User
import com.yoenas.githubusers.databinding.ActivityFavoriteBinding
import com.yoenas.githubusers.ui.detail.DetailActivity
import com.yoenas.githubusers.di.ViewModelFactory
import com.yoenas.githubusers.utils.OnItemClickCallback
import java.util.*

class FavoriteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFavoriteBinding
    private lateinit var favoriteViewModel: FavoriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityFavoriteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        favoriteViewModel = obtainViewModel(this)
        favoriteViewModel.getFavoriteUsers()
        favoriteViewModel.favoriteUsers.observe(this) {
            if (it?.isEmpty() != true) {
                it?.let { it1 -> setFavoriteUsers(it1) }
            } else {
                binding.tvDialogInformation.visibility = View.VISIBLE
                binding.imgDialogInformation.visibility = View.VISIBLE
                binding.rvFavorite.visibility = View.GONE
            }
        }
    }

    private fun setFavoriteUsers(user: List<DetailUser>) {
        binding.apply {
            tvDialogInformation.visibility = View.GONE
            imgDialogInformation.visibility = View.GONE
            rvFavorite.visibility = View.VISIBLE
            with(rvFavorite) {
                layoutManager = GridLayoutManager(applicationContext, 2)
                val listFavoriteUsers = mapList(user)
                val favoriteUsersAdapter = UserAdapter(listFavoriteUsers)
                favoriteUsersAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onItemClicked(user: User) {
                        val intent = Intent(applicationContext, DetailActivity::class.java)
                        intent.putExtra(DetailActivity.EXTRA_DATA_USERNAME, user.login)
                        startActivity(intent)
                    }
                })
                adapter = favoriteUsersAdapter
            }
        }
    }

    private fun mapList(favoriteUsers: List<DetailUser>): ArrayList<User> {
        val userList = ArrayList<User>()
        for (users in favoriteUsers) {
            val mapUser = User(users.login, users.avatarUrl, users.htmlUrl)
            userList.add(mapUser)
        }
        return userList
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteUsers()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }
}