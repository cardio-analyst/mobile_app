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