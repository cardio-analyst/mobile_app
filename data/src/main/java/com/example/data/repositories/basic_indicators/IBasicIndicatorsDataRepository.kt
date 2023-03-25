package com.example.data.repositories.basic_indicators

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.basic_indicators.sources.entities.*
import kotlinx.coroutines.flow.Flow

interface IBasicIndicatorsDataRepository : Repository {
    /**
     * Get all available BasicIndicators
     * @return [Flow]
     */
    fun getBasicIndicators(): Flow<ResultState<List<GetBasicIndicatorResponseEntity>>>

    /**
     * Create new user BasicIndicators record
     * @param createBasicIndicatorRequestEntity [CreateBasicIndicatorRequestEntity]
     * @return [Flow]
     */
    fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)
            : Flow<ResultState<CreateBasicIndicatorResponseEntity>>

    /**
     * Update user BasicIndicators record
     * @param updateBasicIndicatorIdEntity [UpdateBasicIndicatorIdEntity]
     * @return [Flow]
     */
    fun updateBasicIndicator(
        updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity
    ): Flow<ResultState<UpdateBasicIndicatorResponseEntity>>

    /**
     * Reload getting all available diseases to BasicIndicators
     */
    fun reloadBasicIndicators()

    /**
     * Reload creating new user BasicIndicators record
     * @param createBasicIndicatorRequestEntity [CreateBasicIndicatorRequestEntity]
     */
    fun reloadCreateBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)

    /**
     * Reload updating user BasicIndicators record
     * @param updateBasicIndicatorIdEntity [UpdateBasicIndicatorIdEntity]
     */
    fun reloadUpdateBasicIndicator(
        updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity
    )

    /**
     * Get CVE risk for user
     * @param getCVERiskRequestEntity [GetCVERiskRequestEntity]
     */
    fun getCVERisk(
        getCVERiskRequestEntity: GetCVERiskRequestEntity
    ): Flow<ResultState<GetCVERiskResponseEntity>>

    /**
     * Get user Ideal Age
     * @param getCVERiskRequestEntity [GetCVERiskRequestEntity]
     */
    fun getIdealAge(
        getCVERiskRequestEntity: GetCVERiskRequestEntity
    ): Flow<ResultState<GetIdealAgeResponseEntity>>

}