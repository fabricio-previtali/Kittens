package com.example.kittens.data

import com.example.kittens.model.CatByBreedResponseApi
import com.example.kittens.network.CatApiService

interface CatsRepository {
    suspend fun getAllCatsByBreed(): List<CatByBreedResponseApi>
}

class NetworkCatsRepository(
    private val catApiService: CatApiService,
) : CatsRepository {
    override suspend fun getAllCatsByBreed(): List<CatByBreedResponseApi> = catApiService.getAllCatsByBreed()
}
