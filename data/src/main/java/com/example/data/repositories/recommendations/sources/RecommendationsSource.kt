package com.example.data.repositories.recommendations.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseDataEntity

interface RecommendationsSource {

    /**
     * Get the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getRecommendations(): List<GetRecommendationsResponseDataEntity>

    /**
     * Send user information to email (doctor/himSelf)
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [SendReportResponseDataEntity]
     */
    suspend fun sendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity): SendReportResponseDataEntity

}