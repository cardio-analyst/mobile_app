package com.example.lifestyle.domain

import com.example.common.flows.ResultState
import com.example.lifestyle.domain.entities.LifestyleEntity
import kotlinx.coroutines.flow.Flow

interface LifestyleRepository {
    /**
     * Get lifestyle data to check
     * @return [Flow]
     */
    fun getUserLifestyle(): Flow<ResultState<LifestyleEntity>>

    /**
     * Set user lifestyle data
     * @param lifestyleEntity [LifestyleEntity]
     * @return [Flow]
     */
    fun setUserLifestyle(lifestyleEntity: LifestyleEntity): Flow<ResultState<LifestyleEntity>>

    /**
     * Reload get user's lifestyle data request in case error
     */
    fun reloadGetLifestyleUserRequest()

    /**
     * Reload set user's lifestyle data request in case error
     * @param lifestyleEntity [LifestyleEntity]
     */
    fun reloadSetLifestyleUserRequest(lifestyleEntity: LifestyleEntity)
}