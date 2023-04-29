package com.example.data.repositories.diseases

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.diseases.sources.entities.DiseasesDataEntity
import kotlinx.coroutines.flow.Flow

interface IDiseasesDataRepository : Repository {
    /**
     * Get all available diseases to check
     * @return [Flow]
     */
    fun getUserDiseases(): Flow<ResultState<DiseasesDataEntity>>

    /**
     * Set user diseases
     * @param diseasesDataEntity [DiseasesDataEntity]
     * @return [Flow]
     */
    fun setUserDiseases(diseasesDataEntity: DiseasesDataEntity): Flow<ResultState<DiseasesDataEntity>>

    /**
     * Reload get user's diseases request in case error
     */
    fun reloadGetDiseasesUserRequest()

    /**
     * Reload set user's diseases request in case error
     * @param diseasesDataEntity [DiseasesDataEntity]
     */
    fun reloadSetDiseasesUserRequest(diseasesDataEntity: DiseasesDataEntity)

}