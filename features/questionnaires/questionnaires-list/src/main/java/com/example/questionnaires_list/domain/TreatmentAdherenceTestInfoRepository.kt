package com.example.questionnaires_list.domain

import com.example.common.flows.ResultState
import com.example.questionnaires_list.domain.entities.TreatmentAdherence
import kotlinx.coroutines.flow.Flow

interface TreatmentAdherenceTestInfoRepository {
    fun getUserTreatmentAdherence(): Flow<ResultState<TreatmentAdherence>>

    fun reloadGetUserTreatmentAdherence()
}