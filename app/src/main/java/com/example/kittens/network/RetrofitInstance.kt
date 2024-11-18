package com.example.kittens.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    private
    const val BASE_URL = "https://api.thecatapi.com/v1/"
    val catApi: CatApiService by lazy {
        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
//            .client()
            .build()
        retrofit.create(CatApiService::class.java)
    }
}