package com.example.stenocardia_symptoms.presentation

import androidx.lifecycle.viewModelScope
import com.example.common.flows.Success
import com.example.presentation.BaseViewModel
import com.example.stenocardia_symptoms.domain.StenocardiaSymptomsTestRepository
import com.example.stenocardia_symptoms.domain.entities.StenocardiaSymptomsEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StenocardiaSymptomsTestViewModel @Inject constructor(
    private val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository,
    private val stenocardiaSymptomsRouter: StenocardiaSymptomsRouter,
) : BaseViewModel() {

    /**
     * @see StenocardiaSymptomsTestRepository
     */
    private val questions: List<StenocardiaSymptomsTestRepository.Question> =
        stenocardiaSymptomsTestRepository.questions

    private var currentQuestion = -1

    fun getNextQuestion(): StenocardiaSymptomsTestRepository.Question? {
        currentQuestion++
        return if (currentQuestion < questions.size)
            questions[currentQuestion]
        else {
            null
        }
    }

    fun finish(result: Int) = viewModelScope.safeLaunch {
        stenocardiaSymptomsTestRepository.setUserStenocardiaSymptoms(StenocardiaSymptomsEntity(
            anginaScore = result
        )).collect { resultState ->
            if (resultState is Success) {
                stenocardiaSymptomsRouter.goBack()
            }
        }
    }

}