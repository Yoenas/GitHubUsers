package com.yoenas.githubusers.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoenas.githubusers.R
import com.yoenas.githubusers.data.User
import com.yoenas.githubusers.databinding.RowItemUserBinding
import com.yoenas.githubusers.utils.OnItemClickCallback

class UserAdapter(private val listUser: ArrayList<User>) :
    RecyclerView.Adapter<UserAdapter.MyViewHolder>() {

    private var onItemClicked: OnItemClickCallback? = null

    class MyViewHolder(val binding: RowItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.binding.apply {
            with(listUser[position]) {
                tvUsername.text = login
                tvUrl.text = htmlUrl

                // set drawable from uri
                Glide.with(imgAvatar.context).load(avatarUrl)
                    .error(R.drawable.ic_broken_image_white).into(imgAvatar)

                cvRowUser.setOnClickListener {
                    onItemClicked?.onItemClicked(this)
                }
            }
        }
    }

    override fun getItemCount() = listUser.size

    fun setOnItemClickCallback(onItemClicked: OnItemClickCallback) {
        this.onItemClicked = onItemClicked
    }
}