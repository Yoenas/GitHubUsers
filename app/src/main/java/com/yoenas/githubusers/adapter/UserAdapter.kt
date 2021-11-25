package com.yoenas.githubusers.adapter

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoenas.githubusers.R
import com.yoenas.githubusers.data.User
import com.yoenas.githubusers.databinding.RowItemUserBinding
import com.yoenas.githubusers.ui.detail.DetailActivity

class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    class MyViewHolder(val binding: RowItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            tvUsername.text = listUser[position].login
            tvUrl.text = listUser[position].htmlUrl

            // set drawable from uri
            Glide.with(imgAvatar.context).load(listUser[position].avatarUrl)
                .error(R.drawable.ic_broken_image_white).into(imgAvatar)

            cvRowUser.setOnClickListener {
                val intent = Intent(it.context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA_USERNAME, listUser[position].login)
                it.context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = listUser.size
}