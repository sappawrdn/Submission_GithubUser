package com.example.submissiongithub.data.retrofit


import com.example.submissiongithub.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {



    companion object{
        fun getApiService(): ApiService {
            val authInterceptor = Interceptor {chain ->  
                val req = chain.request()
                val mySecretApi = BuildConfig.GITHUB_API_KEY

                val requestHeaders = req.newBuilder()
                    .addHeader("Authorization", mySecretApi)
                    .build()
            chain.proceed(requestHeaders)
            }
            val client = OkHttpClient.Builder()
                .addInterceptor(authInterceptor)
                .build()
            val mySecretUrl = BuildConfig.BASE_URL
            val retrofit = Retrofit.Builder()
                .baseUrl(mySecretUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}