package com.example.registration.domain

import com.example.common.flows.ResultState
import com.example.registration.domain.entities.UserSignUpResponseEntity
import com.example.registration.domain.entities.UserSingUpRequestEntity
import kotlinx.coroutines.flow.Flow

interface UserSignUpRepository {

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
     * Get all available regions
     * @return [List]
     */
    fun getAllAvailableRegions(): List<String>

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


}