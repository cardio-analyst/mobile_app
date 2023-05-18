package com.example.recommendations.domain.entities

data class GetRecommendationsResponseEntity(
    val what: String,
    val why: String,
    val how: String,
)