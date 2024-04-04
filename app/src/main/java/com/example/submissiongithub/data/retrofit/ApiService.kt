package com.example.submissiongithub.data.retrofit

import com.example.submissiongithub.data.response.DetailResponse
import com.example.submissiongithub.data.response.GithubResponse
import com.example.submissiongithub.data.response.ItemsItem
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    fun getUser(@Query("q") username: String): Call<GithubResponse>

    @GET("users/{username}")
    fun getDetailUser(@Path("username") username: String): Call<DetailResponse>

    @GET("/users/{username}/following")
    fun getFollowingUser(@Path("username") username: String): Call<List<ItemsItem>>

    @GET("/users/{username}/followers")
    fun getFollowersUser(@Path("username") username: String): Call<List<ItemsItem>>
}