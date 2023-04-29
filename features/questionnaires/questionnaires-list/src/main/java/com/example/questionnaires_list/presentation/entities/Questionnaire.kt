package com.example.questionnaires_list.presentation.entities

data class Questionnaire(
    val title: String,
    val description: String,
    override val viewType: Int = 0,
    override val onClick: () -> Unit,
) : QuestionnaireItem()
