package com.example.submissiongithub.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.submissiongithub.database.Favorite

class FavoriteDiffCallback(
    private val oldFavoriteList: List<Favorite>,
    private val newFavoriteList: List<Favorite>
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int = oldFavoriteList.size

    override fun getNewListSize(): Int = newFavoriteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldFavoriteList[oldItemPosition].username == newFavoriteList[newItemPosition].username
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavorite = oldFavoriteList[oldItemPosition]
        val newFavorite = newFavoriteList[newItemPosition]
        return oldFavorite.avatarUrl == newFavorite.avatarUrl
    }

}