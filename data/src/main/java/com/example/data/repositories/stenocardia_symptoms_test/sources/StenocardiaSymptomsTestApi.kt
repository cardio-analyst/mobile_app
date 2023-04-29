package com.example.data.repositories.stenocardia_symptoms_test.sources

import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface StenocardiaSymptomsTestApi {
    @GET("tests/angina-rose/info")
    suspend fun getUserStenocardiaSymptoms(): StenocardiaSymptomsDataEntity

    @PUT("tests/angina-rose/edit")
    suspend fun setUserStenocardiaSymptoms(@Body body: StenocardiaSymptomsDataEntity): StenocardiaSymptomsDataEntity
}