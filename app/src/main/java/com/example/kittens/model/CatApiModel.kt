package com.example.kittens.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class CatByBreedResponseApi(
    val name: String,
    val temperament: String,
    val origin: String,
    val description: String,
    @SerialName("country_code") val countryCode: String,
    val image: CatImage? = null,
)

@Serializable
data class CatImage(
    val url: String,
)
