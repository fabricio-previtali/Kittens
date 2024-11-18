package com.example.kittens.model

import androidx.annotation.StringRes

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
    @StringRes val breed: Int,
    val favourite: Boolean,
)

data class CatDetailedView(
    val url: String,
    @StringRes val origin: Int,
    @StringRes val temperament: Int,
    @StringRes val description: Int,
    val favourite: Boolean,

    )

data class Kitten(
    val weight: Weight,
    val id: String,
    val name: String,
    val cfaUrl: String,
    val vetstreetUrl: String,
    val vcahospitalsUrl: String,
    val temperament: String,
    val origin: String,
    val countryCodes: String,
    val countryCode: String,
    val description: String,
    val lifeSpan: String,
    val indoor: Int,
    val lap: Int,
    val altNames: String,
    val adaptability: Int,
    val affectionLevel: Int,
    val childFriendly: Int,
    val dogFriendly: Int,
    val energyLevel: Int,
    val grooming: Int,
    val healthIssues: Int,
    val intelligence: Int,
    val sheddingLevel: Int,
    val socialNeeds: Int,
    val strangerFriendly: Int,
    val vocalisation: Int,
    val experimental: Int,
    val hairless: Int,
    val natural: Int,
    val rare: Int,
    val rex: Int,
    val suppressedTail: Int,
    val shortLegs: Int,
    val wikipediaUrl: String,
    val hypoallergenic: Int,
    val referenceImageId: String
)

data class Weight(
    val imperial: String,
    val metric: String
)