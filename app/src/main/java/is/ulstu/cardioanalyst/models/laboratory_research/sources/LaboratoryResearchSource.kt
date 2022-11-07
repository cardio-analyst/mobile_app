package `is`.ulstu.cardioanalyst.models.laboratory_research.sources

import `is`.ulstu.cardioanalyst.app.BackendExceptions
import `is`.ulstu.cardioanalyst.app.ConnectionException
import `is`.ulstu.cardioanalyst.app.ParseBackendResponseException
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.*

interface LaboratoryResearchSource {

    /**
     * Get the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getLaboratoryResearches(): List<GetLaboratoryResearchResponseEntity>

    /**
     * Create the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [CreateLaboratoryResearchResponseEntity]
     */
    suspend fun createLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity)
            : CreateLaboratoryResearchResponseEntity

    /**
     * Update the user laboratoryResearch
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UpdateLaboratoryResearchResponseEntity]
     */
    suspend fun updateLaboratoryResearch(
        laboratoryResearchId: Long,
        updateLaboratoryResearchRequestEntity: UpdateLaboratoryResearchRequestEntity
    ): UpdateLaboratoryResearchResponseEntity
}