package com.example.questionnaires_list.domain.entities

data class Questionnaire(
    val title: String,
    val description: String,
    val onClick: () -> Unit,
)
