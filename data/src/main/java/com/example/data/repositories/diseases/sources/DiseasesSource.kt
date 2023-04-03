package com.example.data.repositories.diseases.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.diseases.sources.entities.DiseasesDataEntity

interface DiseasesSource {
    /**
     * Get the user diseases
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [DiseasesDataEntity]
     */
    suspend fun getUserDiseases(): DiseasesDataEntity

    /**
     * Change the user diseases
     * @param diseasesDataEntity [DiseasesDataEntity]
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [DiseasesDataEntity]
     */
    suspend fun setUserDiseases(diseasesDataEntity: DiseasesDataEntity): DiseasesDataEntity
}