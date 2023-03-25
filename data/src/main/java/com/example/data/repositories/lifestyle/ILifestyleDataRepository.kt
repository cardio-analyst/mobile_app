package com.example.data.repositories.lifestyle

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.lifestyle.sources.entities.LifestyleMainEntity
import kotlinx.coroutines.flow.Flow

interface ILifestyleDataRepository : Repository {

    /**
     * Get lifestyle data to check
     * @return [Flow]
     */
    fun getUserLifestyle(): Flow<ResultState<LifestyleMainEntity>>

    /**
     * Set user lifestyle data
     * @param lifestyleMainEntity [LifestyleMainEntity]
     * @return [Flow]
     */
    fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): Flow<ResultState<LifestyleMainEntity>>

    /**
     * Reload get user's lifestyle data request in case error
     */
    fun reloadGetLifestyleUserRequest()

    /**
     * Reload set user's lifestyle data request in case error
     * @param lifestyleMainEntity [LifestyleMainEntity]
     */
    fun reloadSetLifestyleUserRequest(lifestyleMainEntity: LifestyleMainEntity)

    /**
     * Savings temp params
     * (when user do not save changes but start some tests in different fragment)
     */
    fun setCurrentChanges(lifestyleMainEntity: LifestyleMainEntity?)

    /**
     * Get savings temp params
     */
    fun getCurrentChanges(): LifestyleMainEntity?

}