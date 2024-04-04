package com.example.submissiongithub.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update

@Dao
interface FavoriteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(favorite: Favorite)

    @Query("SELECT * FROM FAVORITE")
    fun getAllFavorite(): LiveData<List<Favorite>>

    @Query("SELECT * FROM FAVORITE WHERE username = :username")
    fun getFavorite(username: String): LiveData<Favorite>

    @Delete
    fun delete(favorite: Favorite)
}