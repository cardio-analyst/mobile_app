package com.example.data.repositories.treatment_adherence_test

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import kotlinx.coroutines.flow.Flow

interface ITreatmentAdherenceTestDataRepository : Repository {

    /**
     * Treatment Adherence Test questions
     * Pair<"Question title", "List of available answers">
     */
    val questions: List<Question>


    fun getUserTreatmentAdherence(): Flow<ResultState<TreatmentAdherenceDataEntity>>

    fun removeAllListenersGet()

    fun setUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity): Flow<ResultState<TreatmentAdherenceDataEntity>>

    fun reloadGetUserTreatmentAdherence()

    fun reloadSetUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity)

    data class Question(
        val questionNumber: Int,
        val questionName: String,
        val questionsAnswers: List<String>,
    )
}