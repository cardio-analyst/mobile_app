package com.example.data.repositories.lifestyle.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.lifestyle.sources.entities.LifestyleMainEntity

interface LifestyleSource {
    /**
     * Get the user lifestyle data
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [LifestyleMainEntity]
     */
    suspend fun getUserLifestyle(): LifestyleMainEntity

    /**
     * Change the user lifestyle data
     * @param lifestyleMainEntity [DiseasesMainEntity]
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [LifestyleMainEntity]
     */
    suspend fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): LifestyleMainEntity
}
