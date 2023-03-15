package `is`.ulstu.cardioanalyst.models.basic_indicators.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.*

interface BasicIndicatorsSource {

    /**
     * Get the user BasicIndicators
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getBasicIndicators(): List<GetBasicIndicatorResponseEntity>

    /**
     * Create the user BasicIndicator
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [CreateBasicIndicatorResponseEntity]
     */
    suspend fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)
            : CreateBasicIndicatorResponseEntity

    /**
     * Update the user BasicIndicator
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UpdateBasicIndicatorResponseEntity]
     */
    suspend fun updateBasicIndicator(
        basicIndicatorId: Long,
        updateBasicIndicatorRequestEntity: UpdateBasicIndicatorRequestEntity
    ): UpdateBasicIndicatorResponseEntity

    /**
     * Get the user CVERisk
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [GetCVERiskResponseEntity]
     */
    suspend fun getCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetCVERiskResponseEntity

    /**
     * Get the user ideal age
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [GetIdealAgeResponseEntity]
     */
    suspend fun getIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetIdealAgeResponseEntity

}