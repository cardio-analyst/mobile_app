package com.example.data.repositories.recommendations

import com.example.common.flows.ResultState
import com.example.data.DataRepository
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseDataEntity
import kotlinx.coroutines.flow.Flow

interface IRecommendationsDataDataRepository : DataRepository {

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
    fun sendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity): Flow<ResultState<SendReportResponseDataEntity>>

    /**
     * Reload sending report
     */
    fun reloadSendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity)

}