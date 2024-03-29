package com.example.data.repositories.basic_indicators.sources.entities

data class GetBasicIndicatorResponseDataEntity(
    var id: Long?,
    var weight: Double,
    var height: Double,
    var bodyMassIndex: Double,
    var waistSize: Double,
    var gender: String,
    var sbpLevel: Double,
    var smoking: Boolean,
    var totalCholesterolLevel: Double,
    var cvEventsRiskValue: Int,
    var idealCardiovascularAgesRange: String,
    var createdAt: String,
    val scale: String,
)
