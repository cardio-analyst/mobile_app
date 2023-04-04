package com.example.data.repositories.laboratory_research

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.laboratory_research.sources.entities.*
import kotlinx.coroutines.flow.Flow

interface ILaboratoryResearchDataRepository : Repository {

    /**
     * Get all available diseases to laboratoryResearches
     * @return [Flow]
     */
    fun getLaboratoryResearches(): Flow<ResultState<List<GetLaboratoryResearchResponseDataEntity>>>

    /**
     * Create new user laboratoryResearch
     * @param createLaboratoryResearchRequestDataEntity [CreateLaboratoryResearchRequestDataEntity]
     * @return [Flow]
     */
    fun createLaboratoryResearch(createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity)
            : Flow<ResultState<CreateLaboratoryResearchResponseDataEntity>>

    /**
     * Update user laboratoryResearch
     * @param updateLaboratoryResearchIdDataEntity [UpdateLaboratoryResearchIdDataEntity]
     * @return [Flow]
     */
    fun updateLaboratoryResearch(
        updateLaboratoryResearchIdDataEntity: UpdateLaboratoryResearchIdDataEntity
    ): Flow<ResultState<UpdateLaboratoryResearchResponseDataEntity>>

    /**
     * Reload getting all available diseases to laboratoryResearches
     */
    fun reloadGetLaboratoryResearches()

    /**
     * Reload creating new user laboratoryResearch
     * @param createLaboratoryResearchRequestDataEntity [CreateLaboratoryResearchRequestDataEntity]
     */
    fun reloadCreateLaboratoryResearch(createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity)

    /**
     * Reload updating user laboratoryResearch
     * @param updateLaboratoryResearchIdDataEntity [UpdateLaboratoryResearchIdDataEntity]
     */
    fun reloadUpdateLaboratoryResearch(
        updateLaboratoryResearchIdDataEntity: UpdateLaboratoryResearchIdDataEntity
    )
}