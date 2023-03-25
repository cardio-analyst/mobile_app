package com.example.data.repositories.basic_indicators.sources.entities

data class GetCVERiskRequestEntity(
    val gender: String,
    val smoking: Boolean,
    val sbpLevel: Double,
    val totalCholesterolLevel: Double
)
