package com.yoenas.githubusers.ui.followers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoenas.githubusers.adapter.UserAdapter
import com.yoenas.githubusers.databinding.FragmentFollowersBinding
import com.yoenas.githubusers.ui.detail.DetailActivity

class FollowersFragment : Fragment() {

    private lateinit var followersViewModel: FollowersViewModel
    private lateinit var username: String

    private var _binding: FragmentFollowersBinding? = null

    private val binding get() = _binding as FragmentFollowersBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowersBinding.inflate(layoutInflater)
        followersViewModel = ViewModelProvider(this).get(FollowersViewModel::class.java)
        username = arguments?.getString(DetailActivity.EXTRA_DATA_USERNAME).toString()
        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followersViewModel.getUserFollower(username)
        followersViewModel.getResultFollowers().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.rvFollowers.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = UserAdapter(it)
                    showLoading(false)
                }
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
                rvFollowers.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvFollowers.visibility = View.VISIBLE
            }
        }
    }
}