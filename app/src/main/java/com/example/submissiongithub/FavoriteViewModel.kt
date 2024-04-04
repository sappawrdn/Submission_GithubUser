package com.example.submissiongithub

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.database.Favorite
import com.example.submissiongithub.repository.FavoriteRepository

class FavoriteViewModel(application: Application): ViewModel() {

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    private val mFavoriteRepository: FavoriteRepository = FavoriteRepository(application)

    fun insert(favorite: Favorite){
        mFavoriteRepository.insert(favorite)
    }

    fun delete(favorite: Favorite){
        mFavoriteRepository.delete(favorite)
    }

    fun getFavorite(username: String): LiveData<Favorite> {
        return mFavoriteRepository.getFavorite(username)
    }

    fun getAllFavorite(): LiveData<List<Favorite>>{
        _isLoading.value = true
        val favoriteLiveData = mFavoriteRepository.getAllFavorite()
        favoriteLiveData.observeForever {
            _isLoading.value = false
        }
        return favoriteLiveData
    }

}