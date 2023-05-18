package com.example.questionnaires_list.presentation

import com.example.presentation.BaseRouter

interface QuestionnaireRouter : BaseRouter {

    fun launchDiseasesScreen()

    fun launchBasicIndicatorsScreen()

    fun launchLaboratoryResearchScreen()

    fun launchLifestyleScreen()

    fun launchReportScreen()

    fun launchStenocardiaSymptomsTest()

    fun launchTreatmentAdherenceTest()
}