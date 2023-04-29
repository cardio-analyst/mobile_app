package com.example.questionnaires_list.presentation.entities

import com.example.questionnaires_list.domain.entities.TreatmentAdherence

data class TreatmentAdherenceTest(
    val title: String,
    val description: String,
    val generalResult: Double,
    val treatmentAdherence: TreatmentAdherence,
    override val viewType: Int = 2,
    override val onClick: () -> Unit,
) : QuestionnaireItem()
