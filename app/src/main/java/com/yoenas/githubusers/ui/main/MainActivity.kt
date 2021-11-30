package com.yoenas.githubusers.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.tabs.TabLayoutMediator
import com.yoenas.githubusers.R
import com.yoenas.githubusers.adapter.MainSectionPagerAdapter
import com.yoenas.githubusers.databinding.ActivityMainBinding
import com.yoenas.githubusers.di.ViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import java.util.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel

    private var isDarkModeActive: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar)

        mainViewModel = ViewModelProvider(this)[MainViewModel::class.java]
        mainViewModel.getThemeSettings().observe(this) {
            if (it) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                this.isDarkModeActive = true
                binding.btnImgThemeMenu.setImageResource(R.drawable.ic_dark_mode)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                this.isDarkModeActive = false
                binding.btnImgThemeMenu.setImageResource(R.drawable.ic_light_mode)
            }
        }

        iniView()
    }

    private fun iniView() {
        binding.apply {
            vpMain.adapter = MainSectionPagerAdapter(this@MainActivity)

            val tabList = arrayOf(
                getString(R.string.txt_last_search_result),
                getString(R.string.txt_favorite_users)
            )
            TabLayoutMediator(tabs, vpMain) { tabs, position ->
                tabs.text = tabList[position]
            }.attach()

            btnImgThemeMenu.setOnClickListener {
                mainViewModel.saveThemeSetting(!isDarkModeActive)
                if (!isDarkModeActive) {
                    btnImgThemeMenu.setImageResource(R.drawable.ic_dark_mode)
                } else {
                    btnImgThemeMenu.setImageResource(R.drawable.ic_light_mode)
                }
            }
        }
    }
}