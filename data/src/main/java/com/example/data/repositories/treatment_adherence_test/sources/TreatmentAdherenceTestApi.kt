package com.example.data.repositories.treatment_adherence_test.sources

import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface TreatmentAdherenceTestApi {
    @GET("tests/treatment-adherence/info")
    suspend fun getUserTreatmentAdherence(): TreatmentAdherenceDataEntity

    @PUT("tests/treatment-adherence/edit")
    suspend fun setUserTreatmentAdherence(@Body body: TreatmentAdherenceDataEntity): TreatmentAdherenceDataEntity
}