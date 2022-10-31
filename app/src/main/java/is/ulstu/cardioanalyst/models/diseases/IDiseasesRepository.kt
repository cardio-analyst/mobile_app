package `is`.ulstu.cardioanalyst.models.diseases

import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.foundation.model.Repository
import `is`.ulstu.foundation.model.Result
import kotlinx.coroutines.flow.Flow

interface IDiseasesRepository : Repository {
    /**
     * Get all available diseases to check
     */
    fun getUserDiseases(): Flow<Result<DiseasesMainEntity>>

    /**
     * Set user diseases
     */
    fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): Flow<Result<DiseasesMainEntity>>

    /**
     * Reload get user's diseases request in case error
     */
    fun reloadGetDiseasesUserRequest()

    /**
     * Reload set user's diseases request in case error
     */
    fun reloadSetDiseasesUserRequest(diseasesMainEntity: DiseasesMainEntity)

}