package com.example.recommendations.domain

import com.example.common.flows.ResultState
import com.example.recommendations.domain.entities.GetRecommendationsResponseEntity
import kotlinx.coroutines.flow.Flow

interface RecommendationsRepository {

    /**
     * Get recommendations
     * @return [Flow]
     */
    fun getRecommendations(): Flow<ResultState<List<GetRecommendationsResponseEntity>>>

    /**
     * Reload getting recommendations
     */
    fun reloadGetRecommendations()
}