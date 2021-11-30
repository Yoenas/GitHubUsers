package com.yoenas.githubusers.ui.main.favorite

import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.R
import com.yoenas.githubusers.adapter.UserAdapter
import com.yoenas.githubusers.data.model.DetailUser
import com.yoenas.githubusers.data.model.User
import com.yoenas.githubusers.databinding.FragmentFavoriteBinding
import com.yoenas.githubusers.di.ViewModelFactory
import com.yoenas.githubusers.ui.detail.DetailActivity
import com.yoenas.githubusers.utils.OnItemClickCallback
import java.util.*

class FavoriteFragment : Fragment() {

    private lateinit var favoriteViewModel: FavoriteViewModel

    private var _binding: FragmentFavoriteBinding? = null

    private val binding get() = _binding as FragmentFavoriteBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFavoriteBinding.inflate(layoutInflater)
        favoriteViewModel = obtainViewModel(activity as AppCompatActivity)
        return binding.root
    }

    private fun obtainViewModel(activity: AppCompatActivity): FavoriteViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory)[FavoriteViewModel::class.java]
    }

    override fun onResume() {
        super.onResume()
        favoriteViewModel.getFavoriteUsers()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        favoriteViewModel.getFavoriteUsers()
        favoriteViewModel.favoriteUsers.observe(viewLifecycleOwner) {
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
                layoutManager = GridLayoutManager(context, 2)
                val listFavoriteUsers = mapList(user)
                val favoriteUsersAdapter = UserAdapter(listFavoriteUsers)
                favoriteUsersAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                    override fun onItemClicked(user: User) {
                        val intent = Intent(context, DetailActivity::class.java)
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}