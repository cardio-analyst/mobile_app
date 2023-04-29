package com.example.data.repositories.basic_indicators.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.basic_indicators.sources.entities.*

interface BasicIndicatorsSource {

    /**
     * Get the user BasicIndicators
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getBasicIndicators(): List<GetBasicIndicatorResponseDataEntity>

    /**
     * Create the user BasicIndicator
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [CreateBasicIndicatorResponseDataEntity]
     */
    suspend fun createBasicIndicator(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity)
            : CreateBasicIndicatorResponseDataEntity

    /**
     * Update the user BasicIndicator
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UpdateBasicIndicatorResponseDataEntity]
     */
    suspend fun updateBasicIndicator(
        basicIndicatorId: Long,
        updateBasicIndicatorRequestDataEntity: UpdateBasicIndicatorRequestDataEntity
    ): UpdateBasicIndicatorResponseDataEntity

    /**
     * Get the user CVERisk
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [GetCVERiskResponseDataEntity]
     */
    suspend fun getCVERisk(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): GetCVERiskResponseDataEntity

    /**
     * Get the user ideal age
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [GetIdealAgeResponseDataEntity]
     */
    suspend fun getIdealAge(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): GetIdealAgeResponseDataEntity

}