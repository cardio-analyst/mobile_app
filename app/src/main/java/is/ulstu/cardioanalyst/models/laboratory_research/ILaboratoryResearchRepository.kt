package `is`.ulstu.cardioanalyst.models.laboratory_research

import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.*
import `is`.ulstu.foundation.model.Repository
import `is`.ulstu.foundation.model.Result
import kotlinx.coroutines.flow.Flow

interface ILaboratoryResearchRepository : Repository {

    /**
     * Get all available diseases to laboratoryResearches
     * @return [Flow]
     */
    fun getLaboratoryResearches(): Flow<Result<List<GetLaboratoryResearchResponseEntity>>>

    /**
     * Create new user laboratoryResearch
     * @param createLaboratoryResearchRequestEntity [CreateLaboratoryResearchRequestEntity]
     * @return [Flow]
     */
    fun createLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity)
            : Flow<Result<CreateLaboratoryResearchResponseEntity>>

    /**
     * Update user laboratoryResearch
     * @param updateLaboratoryResearchIdEntity [UpdateLaboratoryResearchIdEntity]
     * @return [Flow]
     */
    fun updateLaboratoryResearch(
        updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity
    ): Flow<Result<UpdateLaboratoryResearchResponseEntity>>

    /**
     * Reload getting all available diseases to laboratoryResearches
     */
    fun reloadGetLaboratoryResearches()

    /**
     * Reload creating new user laboratoryResearch
     * @param createLaboratoryResearchRequestEntity [CreateLaboratoryResearchRequestEntity]
     */
    fun reloadCreateLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity)

    /**
     * Reload updating user laboratoryResearch
     * @param updateLaboratoryResearchIdEntity [UpdateLaboratoryResearchIdEntity]
     */
    fun reloadUpdateLaboratoryResearch(
        updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity
    )
}