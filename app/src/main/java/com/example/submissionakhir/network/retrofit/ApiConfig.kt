package com.example.submissionakhir.network.retrofit

import com.example.submissionakhir.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object ApiConfig {
    private const val BASE_URL = BuildConfig.BASE_URL
    private const val KEY_TOKEN = BuildConfig.TOKEN
    private val logging = Interceptor { chain ->
        val request = chain.request().newBuilder()
            .addHeader("Authorization", KEY_TOKEN)
            .build()
        chain.proceed(request)
    }
    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()
    val instance: ApiService by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
        retrofit.create(ApiService::class.java)
    }
}