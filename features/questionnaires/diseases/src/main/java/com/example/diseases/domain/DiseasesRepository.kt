package com.example.diseases.domain

import com.example.common.flows.ResultState
import com.example.diseases.domain.entities.DiseasesEntity
import kotlinx.coroutines.flow.Flow

interface DiseasesRepository {
    /**
     * Get all available diseases to check
     * @return [Flow]
     */
    fun getUserDiseases(): Flow<ResultState<DiseasesEntity>>

    /**
     * Set user diseases
     * @param diseasesEntity [DiseasesEntity]
     * @return [Flow]
     */
    fun setUserDiseases(diseasesEntity: DiseasesEntity): Flow<ResultState<DiseasesEntity>>

    /**
     * Reload get user's diseases request in case error
     */
    fun reloadGetDiseasesUserRequest()

    /**
     * Reload set user's diseases request in case error
     * @param diseasesEntity [DiseasesEntity]
     */
    fun reloadSetDiseasesUserRequest(diseasesEntity: DiseasesEntity)
}