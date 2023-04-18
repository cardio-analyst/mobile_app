package com.example.questionnaires_list.domain

import com.example.common.flows.ResultState
import com.example.questionnaires_list.domain.entities.StenocardiaSymptoms
import kotlinx.coroutines.flow.Flow

interface StenocardiaSymptomsTestInfoRepository {
    fun getUserStenocardiaSymptoms(): Flow<ResultState<StenocardiaSymptoms>>

    fun reloadGetUserStenocardiaSymptoms()
}