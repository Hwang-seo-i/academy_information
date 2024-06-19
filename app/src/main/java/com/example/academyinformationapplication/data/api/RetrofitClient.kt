package com.example.academyinformationapplication.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private const val baseUrl = "https://api.odcloud.kr"

    private fun getRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(baseUrl)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getRetrofitService(): ApiService = getRetrofit().create(ApiService::class.java)
}