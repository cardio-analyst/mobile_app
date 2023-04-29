package com.example.basic_indicators.domain.entities

data class GetCVERiskRequestEntity(
    val gender: String,
    val smoking: Boolean,
    val sbpLevel: Double,
    val totalCholesterolLevel: Double
)
