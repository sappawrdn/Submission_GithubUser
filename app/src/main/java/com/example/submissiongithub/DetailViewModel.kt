package com.example.submissiongithub

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissiongithub.data.response.DetailResponse
import com.example.submissiongithub.data.retrofit.ApiConfig
import com.example.submissiongithub.database.Favorite
import com.example.submissiongithub.database.FavoriteDao
import com.example.submissiongithub.database.FavoriteRoomDatabase
import com.example.submissiongithub.repository.FavoriteRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailViewModel(application: Application) : AndroidViewModel(application) {

    private lateinit var favoriteDao: FavoriteDao
    private lateinit var favoriteDatabase: FavoriteRoomDatabase


    companion object {
        private const val TAG = "DetailViewModel"
    }

    init {
        favoriteDatabase = FavoriteRoomDatabase.getDatabase(application)
        favoriteDao = favoriteDatabase.favoriteDao()
    }

    private val _detailData = MutableLiveData<DetailResponse>()
    val detailData: LiveData<DetailResponse> = _detailData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading

    fun findDetail(username: String) {
        _isLoading.value = true
        val client = ApiConfig.getApiService().getDetailUser(username)
        client.enqueue(object : Callback<DetailResponse> {
            override fun onResponse(
                call: Call<DetailResponse>,
                response: Response<DetailResponse>
            ) {
                _isLoading.value = false
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        _detailData.value = responseBody
                    }
                } else {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<DetailResponse>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }


}