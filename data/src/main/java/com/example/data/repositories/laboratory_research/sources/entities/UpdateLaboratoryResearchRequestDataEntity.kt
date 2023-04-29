package com.example.data.repositories.laboratory_research.sources.entities

data class UpdateLaboratoryResearchRequestDataEntity(
    val highDensityCholesterol: Double,
    val lowDensityCholesterol: Double,
    val triglycerides: Double,
    val lipoprotein: Double,
    val highlySensitiveCReactiveProtein: Double,
    val atherogenicityCoefficient: Double,
    val creatinine: Double,
    val atheroscleroticPlaquesPresence: Boolean,
)
