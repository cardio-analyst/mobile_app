package com.example.basic_indicators.domain.entities

data class UpdateBasicIndicatorIdEntity(
    val basicIndicatorId: Long,
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
