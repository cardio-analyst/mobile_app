package com.example.data.repositories.lifestyle.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.lifestyle.sources.entities.LifestyleDataEntity

interface LifestyleSource {
    /**
     * Get the user lifestyle data
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [LifestyleDataEntity]
     */
    suspend fun getUserLifestyle(): LifestyleDataEntity

    /**
     * Change the user lifestyle data
     * @param lifestyleDataEntity [DiseasesMainEntity]
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [LifestyleDataEntity]
     */
    suspend fun setUserLifestyle(lifestyleDataEntity: LifestyleDataEntity): LifestyleDataEntity
}
