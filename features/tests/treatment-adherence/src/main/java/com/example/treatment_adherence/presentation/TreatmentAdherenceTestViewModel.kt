package com.example.treatment_adherence.presentation

import androidx.lifecycle.viewModelScope
import com.example.common.flows.Success
import com.example.presentation.BaseViewModel
import com.example.treatment_adherence.domain.TreatmentAdherenceTestRepository
import com.example.treatment_adherence.domain.entities.TreatmentAdherenceEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TreatmentAdherenceTestViewModel @Inject constructor(
    private val treatmentAdherenceTestRepository: TreatmentAdherenceTestRepository,
    private val treatmentAdherenceRouter: TreatmentAdherenceRouter,
) : BaseViewModel() {

    /**
     * @see TreatmentAdherenceTestRepository
     */
    private val questions: Queue<TreatmentAdherenceTestRepository.Question> =
        LinkedList(treatmentAdherenceTestRepository.questions)

    fun getNextQuestion(): TreatmentAdherenceTestRepository.Question? = questions.poll()

    fun finish(results: Triple<Double, Double, Double>) = viewModelScope.safeLaunch {
        treatmentAdherenceTestRepository.setUserTreatmentAdherence(
            treatmentAdherenceEntity = TreatmentAdherenceEntity(
                adherenceDrugTherapy = results.first,
                adherenceMedicalSupport = results.second,
                adherenceLifestyleMod = results.third,
            )
        ).collect { resultState ->
            if (resultState is Success) {
                treatmentAdherenceRouter.goBack()
            }
        }
    }
}