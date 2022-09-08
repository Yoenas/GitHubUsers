package com.yoenas.githubusers.ui.following

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.yoenas.githubusers.adapter.UserAdapter
import com.yoenas.githubusers.databinding.FragmentFollowingBinding
import com.yoenas.githubusers.ui.detail.DetailActivity

class FollowingFragment : Fragment() {

    private var _followingViewModel: FollowingViewModel? = null
    private val followingViewModel get() = _followingViewModel as FollowingViewModel

    private var _username: String? = null
    private val username get() = _username as String

    private var _binding: FragmentFollowingBinding? = null
    private val binding get() = _binding as FragmentFollowingBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _followingViewModel = ViewModelProvider(this)[FollowingViewModel::class.java]
        _username = arguments?.getString(DetailActivity.EXTRA_DATA_USERNAME).toString()
        showLoading(true)

        followingViewModel.getUserFollowing(username)
        followingViewModel.getResultFollowing().observe(viewLifecycleOwner) {
            if (it != null) {
                binding.rvFollowing.apply {
                    layoutManager = LinearLayoutManager(activity)
                    adapter = UserAdapter(it)
                    showLoading(false)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showLoading(loading: Boolean) {
        binding.apply {
            if (loading) {
                progressBar.visibility = View.VISIBLE
                rvFollowing.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvFollowing.visibility = View.VISIBLE
            }
        }
    }
}