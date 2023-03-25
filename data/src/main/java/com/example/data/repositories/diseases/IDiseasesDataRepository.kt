package com.example.data.repositories.diseases

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.diseases.sources.entities.DiseasesMainEntity
import kotlinx.coroutines.flow.Flow

interface IDiseasesDataRepository : Repository {
    /**
     * Get all available diseases to check
     * @return [Flow]
     */
    fun getUserDiseases(): Flow<ResultState<DiseasesMainEntity>>

    /**
     * Set user diseases
     * @param diseasesMainEntity [DiseasesMainEntity]
     * @return [Flow]
     */
    fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): Flow<ResultState<DiseasesMainEntity>>

    /**
     * Reload get user's diseases request in case error
     */
    fun reloadGetDiseasesUserRequest()

    /**
     * Reload set user's diseases request in case error
     * @param diseasesMainEntity [DiseasesMainEntity]
     */
    fun reloadSetDiseasesUserRequest(diseasesMainEntity: DiseasesMainEntity)

}