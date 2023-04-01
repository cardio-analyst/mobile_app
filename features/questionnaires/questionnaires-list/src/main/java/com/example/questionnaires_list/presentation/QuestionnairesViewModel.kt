package com.example.questionnaires_list.presentation

import com.example.presentation.BaseViewModel
import com.example.presentation.uiactions.UiAction
import com.example.questionnaires_list.R
import com.example.questionnaires_list.domain.entities.Questionnaire
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class QuestionnairesViewModel @Inject constructor(
    private val uiAction: UiAction,
    private val questionnaireRouter: QuestionnaireRouter,
) : BaseViewModel() {

    fun getQuestionnairesList(): List<Questionnaire> = listOf(
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