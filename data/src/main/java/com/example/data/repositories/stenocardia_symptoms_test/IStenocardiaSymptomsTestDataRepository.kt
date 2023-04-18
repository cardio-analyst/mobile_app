package com.example.data.repositories.stenocardia_symptoms_test

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import kotlinx.coroutines.flow.Flow

interface IStenocardiaSymptomsTestDataRepository : Repository {

    fun getUserStenocardiaSymptoms(): Flow<ResultState<StenocardiaSymptomsDataEntity>>

    fun setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity): Flow<ResultState<StenocardiaSymptomsDataEntity>>

    fun reloadGetUserStenocardiaSymptoms()

    fun reloadSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity)
}