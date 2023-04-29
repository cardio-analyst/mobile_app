package com.example.questionnaires_list.presentation.entities

sealed class QuestionnaireItem {
    abstract val viewType: Int
    abstract val onClick: () -> Unit
}
