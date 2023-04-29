package com.example.stenocardia_symptoms.domain

import com.example.common.flows.ResultState
import com.example.stenocardia_symptoms.domain.entities.StenocardiaSymptomsEntity
import kotlinx.coroutines.flow.Flow

interface StenocardiaSymptomsTestRepository {

    /**
     * Stenocardia Symptoms Test questions
     * List<
     *      "Question title",
     *      Pair<"List of available answers", True = has risk or False = has not risk>,
     *      Multiple = 2 or Single Mode = 1 for choosing questions
     * >
     */
    val questions: List<Question>

    fun setUserStenocardiaSymptoms(stenocardiaSymptomsEntity: StenocardiaSymptomsEntity): Flow<ResultState<StenocardiaSymptomsEntity>>

    fun reloadSetUserStenocardiaSymptoms(stenocardiaSymptomsEntity: StenocardiaSymptomsEntity)

    data class Question(
        val questionName: String,
        val questionsAnswers: List<Pair<String, Boolean>>,
        val choiceMode: Int
    )
}