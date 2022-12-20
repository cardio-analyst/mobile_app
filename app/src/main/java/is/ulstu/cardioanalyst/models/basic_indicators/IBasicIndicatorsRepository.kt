package `is`.ulstu.cardioanalyst.models.basic_indicators

import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.*
import `is`.ulstu.foundation.model.Repository
import `is`.ulstu.foundation.model.Result
import kotlinx.coroutines.flow.Flow

interface IBasicIndicatorsRepository : Repository {
    /**
     * Get all available BasicIndicators
     * @return [Flow]
     */
    fun getBasicIndicators(): Flow<Result<List<GetBasicIndicatorResponseEntity>>>

    /**
     * Create new user BasicIndicators record
     * @param createBasicIndicatorRequestEntity [CreateBasicIndicatorRequestEntity]
     * @return [Flow]
     */
    fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)
            : Flow<Result<CreateBasicIndicatorResponseEntity>>

    /**
     * Update user BasicIndicators record
     * @param updateBasicIndicatorIdEntity [UpdateBasicIndicatorIdEntity]
     * @return [Flow]
     */
    fun updateBasicIndicator(
        updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity
    ): Flow<Result<UpdateBasicIndicatorResponseEntity>>

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
    ): Flow<Result<GetCVERiskResponseEntity>>

    /**
     * Get user Ideal Age
     * @param getCVERiskRequestEntity [GetCVERiskRequestEntity]
     */
    fun getIdealAge(
        getCVERiskRequestEntity: GetCVERiskRequestEntity
    ): Flow<Result<GetIdealAgeResponseEntity>>

}