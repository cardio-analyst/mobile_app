package com.example.data.repositories.treatment_adherence_test.sources

import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity

interface TreatmentAdherenceTestSource {
    suspend fun getUserTreatmentAdherence(): TreatmentAdherenceDataEntity

    suspend fun setUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity): TreatmentAdherenceDataEntity
}