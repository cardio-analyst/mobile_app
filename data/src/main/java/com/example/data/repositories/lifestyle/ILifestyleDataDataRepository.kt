package com.example.data.repositories.lifestyle

import com.example.common.flows.ResultState
import com.example.data.DataRepository
import com.example.data.repositories.lifestyle.sources.entities.LifestyleDataEntity
import kotlinx.coroutines.flow.Flow

interface ILifestyleDataDataRepository : DataRepository {

    /**
     * Get lifestyle data to check
     * @return [Flow]
     */
    fun getUserLifestyle(): Flow<ResultState<LifestyleDataEntity>>

    /**
     * Set user lifestyle data
     * @param lifestyleDataEntity [LifestyleDataEntity]
     * @return [Flow]
     */
    fun setUserLifestyle(lifestyleDataEntity: LifestyleDataEntity): Flow<ResultState<LifestyleDataEntity>>

    /**
     * Reload get user's lifestyle data request in case error
     */
    fun reloadGetLifestyleUserRequest()

    /**
     * Reload set user's lifestyle data request in case error
     * @param lifestyleDataEntity [LifestyleDataEntity]
     */
    fun reloadSetLifestyleUserRequest(lifestyleDataEntity: LifestyleDataEntity)

}