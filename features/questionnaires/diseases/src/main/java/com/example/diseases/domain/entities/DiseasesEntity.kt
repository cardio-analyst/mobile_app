package com.example.diseases.domain.entities

data class DiseasesEntity(
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
