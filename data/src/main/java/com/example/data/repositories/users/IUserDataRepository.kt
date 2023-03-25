package com.example.data.repositories.users

import com.example.common.flows.ResultState
import com.example.data.Repository
import com.example.data.repositories.users.sources.entities.UserInfoRequestEntity
import com.example.data.repositories.users.sources.entities.UserInfoResponseEntity
import com.example.data.repositories.users.sources.entities.UserSignUpResponseEntity
import com.example.data.repositories.users.sources.entities.UserSingUpRequestEntity
import kotlinx.coroutines.flow.Flow

interface IUserDataRepository : Repository {

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
        userSingUpRequestEntity: UserSingUpRequestEntity
    ): Flow<ResultState<UserSignUpResponseEntity>>

    /**
     * Reload user sign in request in case error
     */
    fun reloadSignUpUserRequest(
        userSingUpRequestEntity: UserSingUpRequestEntity
    )

    /**
     * Change user params
     */
    suspend fun changeUserParams(
        userInfoRequestEntity: UserInfoRequestEntity
    )
}