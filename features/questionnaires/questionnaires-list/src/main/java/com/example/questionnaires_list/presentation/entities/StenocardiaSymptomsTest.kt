package com.example.questionnaires_list.presentation.entities

import com.example.questionnaires_list.domain.entities.StenocardiaSymptoms

data class StenocardiaSymptomsTest(
    val title: String,
    val description: String,
    val stenocardiaSymptoms: StenocardiaSymptoms,
    override val viewType: Int = 1,
    override val onClick: () -> Unit,
) : QuestionnaireItem()
