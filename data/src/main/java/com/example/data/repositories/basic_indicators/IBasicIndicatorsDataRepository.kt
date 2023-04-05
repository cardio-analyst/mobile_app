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
    fun getBasicIndicators(): Flow<ResultState<List<GetBasicIndicatorResponseDataEntity>>>

    /**
     * Create new user BasicIndicators record
     * @param createBasicIndicatorRequestDataEntity [CreateBasicIndicatorRequestDataEntity]
     * @return [Flow]
     */
    fun createBasicIndicator(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity)
            : Flow<ResultState<CreateBasicIndicatorResponseDataEntity>>

    /**
     * Update user BasicIndicators record
     * @param updateBasicIndicatorIdDataEntity [UpdateBasicIndicatorIdDataEntity]
     * @return [Flow]
     */
    fun updateBasicIndicator(
        updateBasicIndicatorIdDataEntity: UpdateBasicIndicatorIdDataEntity
    ): Flow<ResultState<UpdateBasicIndicatorResponseDataEntity>>

    /**
     * Reload getting all available diseases to BasicIndicators
     */
    fun reloadBasicIndicators()

    /**
     * Reload creating new user BasicIndicators record
     * @param createBasicIndicatorRequestDataEntity [CreateBasicIndicatorRequestDataEntity]
     */
    fun reloadCreateBasicIndicator(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity)

    /**
     * Reload updating user BasicIndicators record
     * @param updateBasicIndicatorIdDataEntity [UpdateBasicIndicatorIdDataEntity]
     */
    fun reloadUpdateBasicIndicator(
        updateBasicIndicatorIdDataEntity: UpdateBasicIndicatorIdDataEntity
    )

    /**
     * Get CVE risk for user
     * @param getCVERiskRequestDataEntity [GetCVERiskRequestDataEntity]
     */
    fun getCVERisk(
        getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity
    ): Flow<ResultState<GetCVERiskResponseDataEntity>>

    /**
     * Get user Ideal Age
     * @param getCVERiskRequestDataEntity [GetCVERiskRequestDataEntity]
     */
    fun getIdealAge(
        getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity
    ): Flow<ResultState<GetIdealAgeResponseDataEntity>>

}