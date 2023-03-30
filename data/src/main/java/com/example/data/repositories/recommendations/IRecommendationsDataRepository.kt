package com.example.data.repositories.recommendations

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseEntity
import kotlinx.coroutines.flow.Flow

interface IRecommendationsDataRepository : Repository {

    /**
     * Get recommendations
     * @return [Flow]
     */
    fun getRecommendations(): Flow<ResultState<List<GetRecommendationsResponseDataEntity>>>

    /**
     * Reload getting recommendations
     */
    fun reloadGetRecommendations()

    /**
     * Send report to email
     */
    fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): Flow<ResultState<SendReportResponseEntity>>

    /**
     * Reload sending report
     */
    fun reloadSendReportToEmail(sendReportRequestEntity: SendReportRequestEntity)

}