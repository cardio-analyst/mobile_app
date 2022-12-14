package `is`.ulstu.cardioanalyst.models.diseases.sources

import `is`.ulstu.cardioanalyst.app.BackendExceptions
import `is`.ulstu.cardioanalyst.app.ConnectionException
import `is`.ulstu.cardioanalyst.app.ParseBackendResponseException
import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity

interface DiseasesSource {
    /**
     * Get the user diseases
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [DiseasesMainEntity]
     */
    suspend fun getUserDiseases(): DiseasesMainEntity

    /**
     * Change the user diseases
     * @param diseasesMainEntity [DiseasesMainEntity]
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [DiseasesMainEntity]
     */
    suspend fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): DiseasesMainEntity
}