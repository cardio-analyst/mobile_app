package com.example.authorization.domain

import com.example.common.flows.ResultState
import kotlinx.coroutines.flow.Flow

interface UserSignInRepository {

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

}