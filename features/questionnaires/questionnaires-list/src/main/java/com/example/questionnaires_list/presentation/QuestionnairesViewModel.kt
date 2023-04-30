package com.example.questionnaires_list.presentation

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.common.flows.Success
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import com.example.questionnaires_list.R
import com.example.questionnaires_list.domain.StenocardiaSymptomsTestInfoRepository
import com.example.questionnaires_list.domain.TreatmentAdherenceTestInfoRepository
import com.example.questionnaires_list.domain.entities.StenocardiaSymptoms
import com.example.questionnaires_list.domain.entities.TreatmentAdherence
import com.example.questionnaires_list.presentation.entities.Questionnaire
import com.example.questionnaires_list.presentation.entities.QuestionnaireItem
import com.example.questionnaires_list.presentation.entities.StenocardiaSymptomsTest
import com.example.questionnaires_list.presentation.entities.TreatmentAdherenceTest
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionnairesViewModel @Inject constructor(
    private val stenocardiaSymptomsTestInfoRepository: StenocardiaSymptomsTestInfoRepository,
    private val treatmentAdherenceTestInfoRepository: TreatmentAdherenceTestInfoRepository,
    private val uiAction: UiAction,
    private val questionnaireRouter: QuestionnaireRouter,
) : BaseViewModel(), QuestionnaireRouter by questionnaireRouter {

    private val _questionnairesList = MutableLiveData<ResultState<List<QuestionnaireItem>>>()
    val questionnairesList = _questionnairesList.share()


    private fun getQuestionnaires() = viewModelScope.safeLaunch {
        stenocardiaSymptomsTestInfoRepository.getUserStenocardiaSymptoms()
            .collect { stenocardiaSymptomsResultState ->
                when {
                    stenocardiaSymptomsResultState is Error
                            && stenocardiaSymptomsResultState.error is RefreshTokenExpired -> {
                        throw stenocardiaSymptomsResultState.error
                    }
                    stenocardiaSymptomsResultState is Success -> {
                        treatmentAdherenceTestInfoRepository.getUserTreatmentAdherence()
                            .collect { treatmentAdherenceResultState ->
                                _questionnairesList.value = when {
                                    treatmentAdherenceResultState is Error
                                            && treatmentAdherenceResultState.error is RefreshTokenExpired -> {
                                        throw treatmentAdherenceResultState.error
                                    }
                                    treatmentAdherenceResultState is Success -> {
                                        treatmentAdherenceResultState.map {
                                            buildQuestionnairesList(
                                                stenocardiaSymptomsResultState.value,
                                                treatmentAdherenceResultState.value,
                                            )
                                        }
                                    }
                                    else -> treatmentAdherenceResultState.map(null)
                                }
                            }
                    }
                    else -> _questionnairesList.value = stenocardiaSymptomsResultState.map(null)
                }
            }
    }

    private fun buildQuestionnairesList(
        stenocardiaSymptoms: StenocardiaSymptoms,
        treatmentAdherence: TreatmentAdherence,
    ) = buildList {
        addAll(getQuestionnairesList())
        add(
            StenocardiaSymptomsTest(
                title = uiAction.getString(R.string.angina_symptoms),
                description = "lsdkfgjnklsdfjmgklsdg",
                stenocardiaSymptoms = stenocardiaSymptoms,
                onClick = {
                    questionnaireRouter.launchStenocardiaSymptomsTest()
                },
            )
        )
        add(
            TreatmentAdherenceTest(
                title = uiAction.getString(R.string.treatment_adherence),
                description = "lsdkfgjnklsdfjmgklsdg",
                generalResult = calculateGeneralResult(treatmentAdherence),
                treatmentAdherence = treatmentAdherence,
                onClick = {
                    questionnaireRouter.launchTreatmentAdherenceTest()
                },
            )
        )
    }

    fun getOrReloadQuestionnaires() = getQuestionnaires()
        /*if (firstLoadFlag)
            getQuestionnaires()
        else {
            stenocardiaSymptomsTestInfoRepository.reloadGetUserStenocardiaSymptoms()
            //treatmentAdherenceTestInfoRepository.reloadGetUserTreatmentAdherence()
        }*/

    fun removeListeners() {
        stenocardiaSymptomsTestInfoRepository.removeAllListenersGet()
        treatmentAdherenceTestInfoRepository.removeAllListenersGet()
    }

    private fun calculateGeneralResult(treatmentAdherence: TreatmentAdherence) =
        (treatmentAdherence.adherenceMedicalSupport + 2 * treatmentAdherence.adherenceLifestyleMod + 3 *
                treatmentAdherence.adherenceDrugTherapy) / 6

    private fun getQuestionnairesList(): List<Questionnaire> = listOf(
        Questionnaire(
            title = uiAction.getString(R.string.diseases_title),
            description = uiAction.getString(R.string.diseases_description),
            onClick = { questionnaireRouter.launchDiseasesScreen() },
        ),
        Questionnaire(
            title = uiAction.getString(R.string.basic_indicators_title),
            description = uiAction.getString(R.string.basic_indicators_description),
            onClick = { questionnaireRouter.launchBasicIndicatorsScreen() },
        ),
        Questionnaire(
            title = uiAction.getString(R.string.laboratory_research_title),
            description = uiAction.getString(R.string.laboratory_research_description),
            onClick = { questionnaireRouter.launchLaboratoryResearchScreen() },
        ),
        Questionnaire(
            title = uiAction.getString(R.string.lifestyle_title),
            description = uiAction.getString(R.string.lifestyle_description),
            onClick = { questionnaireRouter.launchLifestyleScreen() },
        ),
    )
}