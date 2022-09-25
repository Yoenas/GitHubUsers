package com.yoenas.githubusers

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.yoenas.githubusers.databinding.RowItemUserBinding

class UserAdapter(private val listUser: ArrayList<User>) :
    ListAdapter<User, UserAdapter.MyViewHolder>(DIFF_CALLBACK) {

    class MyViewHolder(val binding: RowItemUserBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = MyViewHolder(
        RowItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    @SuppressLint("DiscouragedApi")
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val context = holder.binding.root.context
        holder.binding.apply {
            tvUsername.text = listUser[position].username
            tvCompany.text = listUser[position].company

            // set drawable from uri
            val imageResource: Int = context.resources.getIdentifier(listUser[position].avatar, null, imgAvatar.context.packageName)
            Glide.with(context).load(imageResource).into(imgAvatar)

            cvRowUser.setOnClickListener {
                val intent = Intent(context, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_DATA_USER, listUser[position])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemCount() = listUser.size

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<User>() {
            override fun areItemsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: User, newItem: User): Boolean {
                return oldItem.username == newItem.username
            }
        }
    }
}