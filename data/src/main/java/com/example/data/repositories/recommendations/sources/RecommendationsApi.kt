package com.example.data.repositories.recommendations.sources

import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsArrayResponseDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecommendationsApi {

    @GET("recommendations")
    suspend fun getRecommendations(): GetRecommendationsArrayResponseDataEntity

    @POST("recommendations/send")
    suspend fun sendReportToEmail(
        @Body sendReportRequestEntity: SendReportRequestEntity
    ): SendReportResponseEntity

}