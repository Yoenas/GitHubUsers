package com.yoenas.githubusers.ui.main.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.yoenas.githubusers.R
import com.yoenas.githubusers.adapter.UserAdapter
import com.yoenas.githubusers.data.model.User
import com.yoenas.githubusers.databinding.FragmentSearchResultBinding
import com.yoenas.githubusers.ui.detail.DetailActivity
import com.yoenas.githubusers.utils.OnItemClickCallback
import java.util.ArrayList

class SearchResultFragment : Fragment() {

    private lateinit var searchViewModel: SearchResultViewModel

    private var _binding: FragmentSearchResultBinding? = null

    private val binding get() = _binding as FragmentSearchResultBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        menu.clear()
        inflater.inflate(R.menu.main_menu, menu)

        val searchManager = activity?.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.action_search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(requireActivity().componentName))
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

        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun searchUserByQuery(query: String?) {
        if (query?.isEmpty() == true) {
            showLoading(false, null)
        } else {
            query?.let { searchViewModel.searchUser(it) }
            showLoading(true, query)
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

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSearchResultBinding.inflate(layoutInflater)
        searchViewModel = ViewModelProvider(this)[SearchResultViewModel::class.java]
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchViewModel.getResultSearchUser().observe(viewLifecycleOwner) {
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
            userAdapter.setOnItemClickCallback(object : OnItemClickCallback {
                override fun onItemClicked(user: User) {
                    val intent = Intent(context, DetailActivity::class.java)
                    intent.putExtra(DetailActivity.EXTRA_DATA_USERNAME, user.login)
                    startActivity(intent)
                }
            })
            adapter = userAdapter
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}