package com.example.laboratory_research.domain

import com.example.common.flows.ResultState
import com.example.laboratory_research.domain.entities.*
import kotlinx.coroutines.flow.Flow

interface LaboratoryResearchRepository {
    /**
     * Get all available diseases to laboratoryResearches
     * @return [Flow]
     */
    fun getLaboratoryResearches(): Flow<ResultState<List<GetLaboratoryResearchResponseEntity>>>

    /**
     * Create new user laboratoryResearch
     * @param createLaboratoryResearchRequestEntity [CreateLaboratoryResearchRequestEntity]
     * @return [Flow]
     */
    fun createLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity)
            : Flow<ResultState<CreateLaboratoryResearchResponseEntity>>

    /**
     * Update user laboratoryResearch
     * @param updateLaboratoryResearchIdEntity [UpdateLaboratoryResearchIdEntity]
     * @return [Flow]
     */
    fun updateLaboratoryResearch(
        updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity
    ): Flow<ResultState<UpdateLaboratoryResearchResponseEntity>>

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