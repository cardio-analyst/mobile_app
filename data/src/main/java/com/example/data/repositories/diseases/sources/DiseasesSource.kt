package com.example.data.repositories.diseases.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.diseases.sources.entities.DiseasesMainEntity

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