package com.example.treatment_adherence.domain

import com.example.common.flows.ResultState
import com.example.treatment_adherence.domain.entities.TreatmentAdherenceEntity
import kotlinx.coroutines.flow.Flow

interface TreatmentAdherenceTestRepository {
    /**
     * Treatment Adherence Test questions
     * Pair<"Question title", "List of available answers">
     */
    val questions: List<Question>


    fun setUserTreatmentAdherence(treatmentAdherenceEntity: TreatmentAdherenceEntity): Flow<ResultState<TreatmentAdherenceEntity>>

    fun reloadSetUserTreatmentAdherence(treatmentAdherenceEntity: TreatmentAdherenceEntity)

    data class Question(
        val questionNumber: Int,
        val questionName: String,
        val questionsAnswers: List<String>,
    )
}