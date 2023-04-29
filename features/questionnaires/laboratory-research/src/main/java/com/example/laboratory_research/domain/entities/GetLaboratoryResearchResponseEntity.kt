package com.example.laboratory_research.domain.entities

data class GetLaboratoryResearchResponseEntity(
    var id: Long?,
    var highDensityCholesterol: Double,
    var lowDensityCholesterol: Double,
    var triglycerides: Double,
    var lipoprotein: Double,
    var highlySensitiveCReactiveProtein: Double,
    var atherogenicityCoefficient: Double,
    var creatinine: Double,
    var atheroscleroticPlaquesPresence: Boolean,
    var createdAt: String
)
