package com.example.data.repositories.basic_indicators.sources

import com.example.data.repositories.basic_indicators.sources.entities.*
import retrofit2.http.*

interface BasicIndicatorsApi {

    @GET("basicIndicators")
    suspend fun getBasicIndicators(): GetBasicIndicatorsArrayResponseDataEntity

    @POST("basicIndicators")
    suspend fun createBasicIndicator(
        @Body createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity
    ): CreateBasicIndicatorResponseDataEntity

    @PUT("basicIndicators/{basicIndicatorId}")
    suspend fun updateBasicIndicator(
        @Path("basicIndicatorId") basicIndicatorId: Long,
        @Body body: UpdateBasicIndicatorRequestDataEntity
    ): UpdateBasicIndicatorResponseDataEntity

    @GET("score/cveRisk")
    suspend fun getCVERisk(
        @Query("gender") gender: String,
        @Query("smoking") smoking: Boolean,
        @Query("sbpLevel") sbpLevel: Double,
        @Query("totalCholesterolLevel") totalCholesterolLevel: Double
    ): GetCVERiskResponseDataEntity

    @GET("score/idealAge")
    suspend fun getIdealAge(
        @Query("gender") gender: String,
        @Query("smoking") smoking: Boolean,
        @Query("sbpLevel") sbpLevel: Double,
        @Query("totalCholesterolLevel") totalCholesterolLevel: Double
    ): GetIdealAgeResponseDataEntity
}