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

    private lateinit var followingViewModel: FollowingViewModel
    private lateinit var username: String

    private var _binding: FragmentFollowingBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentFollowingBinding.inflate(layoutInflater)
        followingViewModel = ViewModelProvider(this).get(FollowingViewModel::class.java)
        username = arguments?.getString(DetailActivity.EXTRA_DATA_USERNAME).toString()

        showLoading(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        followingViewModel.getUserFollowing(username)
        followingViewModel.getResultFollowing().observe(viewLifecycleOwner, {
            if (it != null) {
                binding.rvFollowing.apply {
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
                rvFollowing.visibility = View.GONE
            } else {
                progressBar.visibility = View.GONE
                rvFollowing.visibility = View.VISIBLE
            }
        }
    }
}