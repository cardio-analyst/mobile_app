package com.example.treatment_adherence.domain.entities

data class TreatmentAdherenceEntity(
    val adherenceDrugTherapy: Double,
    val adherenceMedicalSupport: Double,
    val adherenceLifestyleMod: Double,
)