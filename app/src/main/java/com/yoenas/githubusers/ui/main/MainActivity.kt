package com.yoenas.githubusers.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.R
import com.yoenas.githubusers.adapter.UserAdapter
import com.yoenas.githubusers.data.model.User
import com.yoenas.githubusers.databinding.ActivityMainBinding
import com.yoenas.githubusers.ui.detail.DetailActivity
import com.yoenas.githubusers.ui.favorite.FavoriteActivity
import com.yoenas.githubusers.utils.OnItemClickCallback
import java.util.*

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getResultSearchUser().observe(this) {
            setUser(it)
            if (it != null) {
                showLoading(false, null)
            }
        }
    }

    private fun setUser(user: ArrayList<User>) {
        binding.rvUser.apply {
            layoutManager = GridLayoutManager(context, 2)
            val userAdapter = UserAdapter(user)
            adapter = userAdapter
            userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    val intent = Intent(this@MainActivity, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA_USERNAME, user.login)
                    startActivity(intent)
                }
            })
        }
    }

    private fun showLoading(loading: Boolean, username: String?) {
        binding.apply {
            if (loading) {
                tvDialogInformation.visibility = View.GONE
                imgDialogInformation.visibility = View.GONE
                progressBar.visibility = View.VISIBLE
                tvSearchProgress.visibility = View.VISIBLE
                tvSearchQuery.text = username
                tvSearchQuery.visibility = View.VISIBLE
                rvUser.visibility = View.GONE
            } else {
                tvDialogInformation.visibility = View.GONE
                imgDialogInformation.visibility = View.GONE
                progressBar.visibility = View.GONE
                tvSearchProgress.visibility = View.GONE
                tvSearchQuery.visibility = View.GONE
                rvUser.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.txt_type_the_username)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                searchUserByQuery(query)
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                searchUserByQuery(newText)
                return true
            }

        })

        return super.onCreateOptionsMenu(menu)
    }

    private fun searchUserByQuery(query: String?) {
        if (query?.isEmpty() == true) {
            showLoading(false, null)
        } else {
            query?.let { mainViewModel.searchUser(it) }
            showLoading(true, query)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_favorite -> startActivity(Intent(this, FavoriteActivity::class.java))
        }
        return super.onOptionsItemSelected(item)
    }
}