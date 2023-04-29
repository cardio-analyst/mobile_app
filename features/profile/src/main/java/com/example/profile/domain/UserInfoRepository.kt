package com.example.profile.domain

import com.example.common.flows.ResultState
import com.example.profile.domain.entities.UserInfoRequestEntity
import com.example.profile.domain.entities.UserInfoResponseEntity
import kotlinx.coroutines.flow.Flow

interface UserInfoRepository {

    /**
     * Get all available regions
     * @return [List]
     */
    fun getAllAvailableRegions(): List<String>

    /**
     * Get user information
     * @return [Flow]
     */
    fun getCurrentUserInfo(): Flow<ResultState<UserInfoResponseEntity>>

    /**
     * Reload user information
     */
    fun reloadCurrentUserInfo()

    /**
     * Change user params
     */
    suspend fun changeUserParams(
        userInfoRequestEntity: UserInfoRequestEntity
    )

    /**
     * Logout current user
     */
    fun logoutUser()
}