package com.example.submissiongithub

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissiongithub.data.response.ItemsItem
import com.example.submissiongithub.data.retrofit.ApiConfig
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FollowerViewModel: ViewModel() {

    companion object{
        private const val TAG = "FollowerViewModel"
    }

    private val _folliwData = MutableLiveData<List<ItemsItem>>()
    val folliwData: LiveData<List<ItemsItem>> = _folliwData

    private val _followData = MutableLiveData<List<ItemsItem>>()
    val followData: LiveData<List<ItemsItem>> = _followData

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> = _isLoading


    fun followUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowersUser(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _followData.value = response.body()
                    Log.e(TAG, "akun: $username")
                }else  {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

    fun folliwUser(username: String){
        _isLoading.value = true
        val client = ApiConfig.getApiService().getFollowingUser(username)
        client.enqueue(object : Callback<List<ItemsItem>> {
            override fun onResponse(
                call: Call<List<ItemsItem>>,
                response: Response<List<ItemsItem>>
            ) {
                _isLoading.value = false
                if(response.isSuccessful){
                    _folliwData.value = response.body()
                    Log.e(TAG, "akun: $username")
                }else  {
                    Log.e(TAG, "onFailure: ${response.message()}")
                }
            }

            override fun onFailure(call: Call<List<ItemsItem>>, t: Throwable) {
                _isLoading.value = false
                Log.e(TAG, "onFailure: ${t.message}")
            }

        })
    }

}