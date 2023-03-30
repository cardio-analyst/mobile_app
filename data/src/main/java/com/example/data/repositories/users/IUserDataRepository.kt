package com.example.data.repositories.users

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.users.sources.entities.UserInfoRequestDataEntity
import com.example.data.repositories.users.sources.entities.UserInfoResponseDataEntity
import com.example.data.repositories.users.sources.entities.UserSignUpResponseDataEntity
import com.example.data.repositories.users.sources.entities.UserSingUpRequestDataEntity
import kotlinx.coroutines.flow.Flow

interface IUserDataRepository : Repository {

    /**
     * Get user information
     * @return [Flow]
     */
    fun getCurrentUserInfo(): Flow<ResultState<UserInfoResponseDataEntity>>

    /**
     * Reload user information
     */
    fun reloadCurrentUserInfo()

    /**
     * Get all available regions
     * @return [List]
     */
    fun getAllAvailableRegions(): List<String>

    /**
     * Get user access token
     */
    suspend fun getUserAccessToken(): String?

    /**
     * Refresh access token for user account session
     */
    suspend fun refreshUserAccessToken()

    /**
     * Enter user by [loginOrEmail] and [password]
     * @param loginOrEmail [String]
     * @param password [String]
     * @return [Flow]
     */
    fun signInUser(loginOrEmail: String, password: String): Flow<ResultState<Unit>>

    /**
     * Reload user sign in request in case error
     * @param loginOrEmail [String]
     * @param password [String]
     */
    fun reloadSignInUserRequest(loginOrEmail: String, password: String)

    /**
     * Logout current user
     */
    fun logoutUser()

    /**
     * Register new user
     */
    fun signUpUser(
        userSingUpRequestDataEntity: UserSingUpRequestDataEntity
    ): Flow<ResultState<UserSignUpResponseDataEntity>>

    /**
     * Reload user sign in request in case error
     */
    fun reloadSignUpUserRequest(
        userSingUpRequestDataEntity: UserSingUpRequestDataEntity
    )

    /**
     * Change user params
     */
    suspend fun changeUserParams(
        userInfoRequestDataEntity: UserInfoRequestDataEntity
    )
}