package com.example.submissiongithub.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.example.submissiongithub.database.Favorite
import com.example.submissiongithub.database.FavoriteDao
import com.example.submissiongithub.database.FavoriteRoomDatabase
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class FavoriteRepository(application: Application) {
    private val mfavoriteDao: FavoriteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init{
        val db = FavoriteRoomDatabase.getDatabase(application)
        mfavoriteDao = db.favoriteDao()
    }

    fun getAllFavorite(): LiveData<List<Favorite>> = mfavoriteDao.getAllFavorite()

    fun insert(favorite: Favorite){
        executorService.execute{mfavoriteDao.insert(favorite)}
    }

    fun delete(favorite: Favorite){
        executorService.execute{mfavoriteDao.delete(favorite)}
    }

    fun getFavorite(username: String): LiveData<Favorite>{
        return mfavoriteDao.getFavorite(username)
    }
}