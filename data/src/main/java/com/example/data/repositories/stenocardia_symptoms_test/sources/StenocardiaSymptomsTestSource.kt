package com.example.data.repositories.stenocardia_symptoms_test.sources

import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity

interface StenocardiaSymptomsTestSource {
    suspend fun getUserStenocardiaSymptoms(): StenocardiaSymptomsDataEntity

    suspend fun setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity): StenocardiaSymptomsDataEntity
}