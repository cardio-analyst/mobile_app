package com.example.laboratory_research.domain.entities

data class UpdateLaboratoryResearchIdEntity(
    val laboratoryResearchId: Long,
    val highDensityCholesterol: Double,
    val lowDensityCholesterol: Double,
    val triglycerides: Double,
    val lipoprotein: Double,
    val highlySensitiveCReactiveProtein: Double,
    val atherogenicityCoefficient: Double,
    val creatinine: Double,
    val atheroscleroticPlaquesPresence: Boolean,
)
