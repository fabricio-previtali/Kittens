package com.example.kittens.model

data class CatApiResponse(
    val id: String,
    val url: String,
    val width: Int,
    val height: Int,
    val breeds: List<Any>,
    val favourite: Any,

    )

data class Cat(
    val url: String,
    val breed: String,
    val favourite: Boolean,
)

data class CatDetailedView(
    val url: String,
    val origin: String,
    val temperament: String,
    val description: String,
    val favourite: Boolean,

    )