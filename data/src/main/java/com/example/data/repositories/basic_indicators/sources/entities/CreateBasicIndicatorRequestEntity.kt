package com.example.data.repositories.basic_indicators.sources.entities

data class CreateBasicIndicatorRequestEntity(
    val weight: Double,
    val height: Double,
    val bodyMassIndex: Double,
    val waistSize: Double,
    val gender: String,
    val sbpLevel: Double,
    val smoking: Boolean,
    val totalCholesterolLevel: Double,
    val cvEventsRiskValue: Int,
    val idealCardiovascularAgesRange: String
)
