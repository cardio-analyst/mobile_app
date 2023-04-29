package com.example.data.repositories.diseases.sources.entities

data class DiseasesDataEntity(
    val cvdPredisposed: Boolean,
    val takesStatins: Boolean,
    val hasChronicKidneyDisease: Boolean,
    val hasArterialHypertension: Boolean,
    val hasIschemicHeartDisease: Boolean,
    val hasTypeTwoDiabetes: Boolean,
    val hadInfarctionOrStroke: Boolean,
    val hasAtherosclerosis: Boolean,
    val hasOtherCVD: Boolean
)
