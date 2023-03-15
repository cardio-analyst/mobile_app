package `is`.ulstu.cardioanalyst.models.lifestyle.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity

interface LifestyleSource {
    /**
     * Get the user lifestyle data
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [LifestyleMainEntity]
     */
    suspend fun getUserLifestyle(): LifestyleMainEntity

    /**
     * Change the user lifestyle data
     * @param lifestyleMainEntity [DiseasesMainEntity]
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [LifestyleMainEntity]
     */
    suspend fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): LifestyleMainEntity
}
