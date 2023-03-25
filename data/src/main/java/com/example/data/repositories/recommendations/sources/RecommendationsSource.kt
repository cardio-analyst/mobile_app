package com.example.data.repositories.recommendations.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseEntity

interface RecommendationsSource {

    /**
     * Get the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getRecommendations(): List<GetRecommendationsResponseEntity>

    /**
     * Send user information to email (doctor/himSelf)
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [SendReportResponseEntity]
     */
    suspend fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): SendReportResponseEntity

}