package com.example.data.repositories.laboratory_research.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.laboratory_research.sources.entities.*

interface LaboratoryResearchSource {

    /**
     * Get the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getLaboratoryResearches(): List<GetLaboratoryResearchResponseDataEntity>

    /**
     * Create the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [CreateLaboratoryResearchResponseDataEntity]
     */
    suspend fun createLaboratoryResearch(createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity)
            : CreateLaboratoryResearchResponseDataEntity

    /**
     * Update the user laboratoryResearch
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UpdateLaboratoryResearchResponseDataEntity]
     */
    suspend fun updateLaboratoryResearch(
        laboratoryResearchId: Long,
        updateLaboratoryResearchRequestDataEntity: UpdateLaboratoryResearchRequestDataEntity
    ): UpdateLaboratoryResearchResponseDataEntity
}