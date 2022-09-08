package com.yoenas.githubusers.ui.detail

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.yoenas.githubusers.R
import com.yoenas.githubusers.adapter.SectionPagerAdapter
import com.yoenas.githubusers.data.DetailUser
import com.yoenas.githubusers.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private var _binding: ActivityDetailBinding? = null
    private val binding get() = _binding as ActivityDetailBinding

    private var _detailViewModel: DetailViewModel? = null
    private val detailViewModel get() = _detailViewModel as DetailViewModel

    private var _dataUser: DetailUser? = null
    private val dataUser get() = _dataUser as DetailUser

    private var saveDataUser = Bundle()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        _binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
            title = getString(R.string.txt_title_detail)
        }

        val username = intent.getStringExtra(EXTRA_DATA_USERNAME)

        saveDataUser.putString(EXTRA_DATA_USERNAME, username)

        _detailViewModel = ViewModelProvider(this)[DetailViewModel::class.java]
        detailViewModel.getUser(username!!)
        detailViewModel.getUserDetails().observe(this) {
            _dataUser = it
            showLoading(true)
            if (it != null) {
                initView()
            }

        }
    }

    private fun initView() {
        val tabList = arrayOf(getString(R.string.txt_followers), getString(R.string.txt_following))

        binding.apply {
            with(dataUser) {
                Glide.with(applicationContext).load(avatarUrl)
                    .error(R.drawable.ic_broken_image_dark)
                    .into(imgAvatarDetail)
                tvNameDetail.text = name
                tvUsernameDetail.text = login
                tvCompanyDetail.text = company
                tvLocationDetail.text = location
                tvRepositaryDetail.text = publicRepos.toString()
                tvFollowersDetail.text = followers.toString()
                tvFollowingDetail.text = following.toString()
            }

            vpFollow.adapter = SectionPagerAdapter(this@DetailActivity, saveDataUser)
            TabLayoutMediator(tabs, vpFollow) { tab, position ->
                tab.text = tabList[position]
            }.attach()
        }
        showLoading(false)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return super.onSupportNavigateUp()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_menu_share -> shareUserProfile()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun shareUserProfile() {
        dataUser.apply {
            val textValue = "This is $name's GitHub Profile\n" +
                    "Username: $login\n" +
                    "Company: $company\n" +
                    "Location: $location\n" +
                    "Total $publicRepos Repositories\n" +
                    "$followers Followers & $following Following\n" +
                    "Visit link $htmlUrl"

            val sendIntent: Intent = Intent().apply {
                action = Intent.ACTION_SEND
                putExtra(Intent.EXTRA_TEXT, textValue)
                type = "text/plain"
            }

            val shareIntent = Intent.createChooser(sendIntent, getString(R.string.txt_share_to))
            startActivity(shareIntent)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
                imgAvatarDetail.visibility = View.GONE
                imgCompany.visibility = View.GONE
                imgLocation.visibility = View.GONE
                imgRepo.visibility = View.GONE
                tvRepo.visibility = View.GONE
                tvFollowers.visibility = View.GONE
                tvFollowing.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                imgAvatarDetail.visibility = View.VISIBLE
                imgCompany.visibility = View.VISIBLE
                imgLocation.visibility = View.VISIBLE
                imgRepo.visibility = View.VISIBLE
                tvRepo.visibility = View.VISIBLE
                tvFollowers.visibility = View.VISIBLE
                tvFollowing.visibility = View.VISIBLE
            }
        }
    }

    companion object {
        const val EXTRA_DATA_USERNAME = "username"
    }
}