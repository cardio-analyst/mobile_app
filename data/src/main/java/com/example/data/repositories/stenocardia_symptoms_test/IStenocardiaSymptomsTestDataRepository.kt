package com.example.data.repositories.stenocardia_symptoms_test

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import kotlinx.coroutines.flow.Flow

interface IStenocardiaSymptomsTestDataRepository : Repository {

    /**
     * Stenocardia Symptoms Test questions
     * List<
     *      "Question title",
     *      Pair<"List of available answers", True = has risk or False = has not risk>,
     *      Multiple = 2 or Single Mode = 1 for choosing questions
     * >
     */
    val questions: List<Question>

    fun getUserStenocardiaSymptoms(): Flow<ResultState<StenocardiaSymptomsDataEntity>>

    fun removeAllListenersGet()

    fun setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity): Flow<ResultState<StenocardiaSymptomsDataEntity>>

    fun reloadGetUserStenocardiaSymptoms()

    fun reloadSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity)

    data class Question(
        val questionName: String,
        val questionsAnswers: List<Pair<String, Boolean>>,
        val choiceMode: Int
    )
}