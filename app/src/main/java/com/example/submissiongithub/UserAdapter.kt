package com.example.submissiongithub

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CircleCrop
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.databinding.ItemUserBinding

class UserAdapter : ListAdapter<ItemsItem, UserAdapter.MyViewHolder>(diffCallback) {

    class MyViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: ItemsItem) {
            Glide.with(binding.root)
                .load(user.avatarUrl)
                .transform(CircleCrop())
                .into(binding.ivUser)
            binding.tvUser.text = user.login

            binding.root.setOnClickListener {
                val intent = Intent(binding.root.context, DetailActivity::class.java)

                intent.putExtra(EXTRA_USERNAME, user.login)
                intent.putExtra(EXTRA_URL, user.avatarUrl)
                intent.putExtra(EXTRA_HTML, user.htmlUrl)
                binding.root.context.startActivity(intent)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }


    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ItemsItem>() {
            override fun areItemsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: ItemsItem, newItem: ItemsItem): Boolean {
                return oldItem == newItem
            }
        }

        const val EXTRA_USERNAME = "username"
        const val EXTRA_URL = "avatarUrl"
        const val EXTRA_HTML = "htmlUrl"
    }

}