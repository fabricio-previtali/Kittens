package com.example.kittens.network

import com.example.kittens.BuildConfig
import com.example.kittens.data.AppContainer
import com.example.kittens.data.CatsRepository
import com.example.kittens.data.NetworkCatsRepository
import com.example.kittens.model.CatByBreedResponseApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Response
import retrofit2.Retrofit
import retrofit2.http.GET

class ApiKeyInterceptor(
    private val apiKey: String,
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request =
            chain
                .request()
                .newBuilder()
                .addHeader("x-api-key", apiKey)
                .build()
        return chain.proceed(request)
    }
}

class DefaultAppContainer : AppContainer {
    private val apiKey = BuildConfig.API_KEY
    private val okHttpClient =
        OkHttpClient.Builder().addInterceptor(ApiKeyInterceptor(apiKey)).build()
    private val baseUrl = "https://api.thecatapi.com/v1/"

    private val networkJson = Json { ignoreUnknownKeys = true }

    private val retrofit =
        Retrofit
            .Builder()
            .client(okHttpClient)
            .addConverterFactory(networkJson.asConverterFactory("application/json".toMediaType()))
            .baseUrl(baseUrl)
            .build()
    private val retrofitService: CatApiService by lazy {
        retrofit.create(CatApiService::class.java)
    }

    override val catsRepository: CatsRepository by lazy {
        NetworkCatsRepository(retrofitService)
    }
}

interface CatApiService {
    @GET("breeds?attach_image=1")
    suspend fun getAllCatsByBreed(): List<CatByBreedResponseApi>
}
