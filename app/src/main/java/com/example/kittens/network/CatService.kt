package com.example.kittens.network

import com.example.kittens.BuildConfig
import com.example.kittens.model.CatApiResponse
import com.example.kittens.model.Kitten
import retrofit2.http.GET
import retrofit2.http.Headers

const val apiKey = BuildConfig.API_KEY

interface CatApiService {
    @Headers("x-api-key: $apiKey")
    @GET("images/search?limit=10")
    suspend fun getCats(): List<CatApiResponse>

    @Headers("x-api-key: $apiKey")
    @GET("breeds")
    suspend fun getKittens(): List<Kitten>
}