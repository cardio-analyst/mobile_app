package com.example.data.repositories.treatment_adherence_test

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import kotlinx.coroutines.flow.Flow

interface ITreatmentAdherenceTestDataRepository : Repository {
    fun getUserTreatmentAdherence(): Flow<ResultState<TreatmentAdherenceDataEntity>>

    fun setUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity): Flow<ResultState<TreatmentAdherenceDataEntity>>

    fun reloadGetUserTreatmentAdherence()

    fun reloadSetUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity)
}