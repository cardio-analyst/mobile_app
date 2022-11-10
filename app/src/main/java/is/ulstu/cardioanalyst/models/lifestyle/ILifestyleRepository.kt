package `is`.ulstu.cardioanalyst.models.lifestyle

import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.foundation.model.Repository
import `is`.ulstu.foundation.model.Result
import kotlinx.coroutines.flow.Flow

interface ILifestyleRepository : Repository {

    /**
     * Get lifestyle data to check
     * @return [Flow]
     */
    fun getUserLifestyle(): Flow<Result<LifestyleMainEntity>>

    /**
     * Set user lifestyle data
     * @param lifestyleMainEntity [LifestyleMainEntity]
     * @return [Flow]
     */
    fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): Flow<Result<LifestyleMainEntity>>

    /**
     * Reload get user's lifestyle data request in case error
     */
    fun reloadGetLifestyleUserRequest()

    /**
     * Reload set user's lifestyle data request in case error
     * @param lifestyleMainEntity [LifestyleMainEntity]
     */
    fun reloadSetLifestyleUserRequest(lifestyleMainEntity: LifestyleMainEntity)

}