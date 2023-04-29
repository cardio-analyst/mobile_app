package com.example.data

import com.example.common.AccessTokenExpired
import com.example.data.repositories.users.IUserDataRepository

/**
 * Base interface for all repositories
 */
interface Repository {

    suspend fun <T> wrapBackendExceptions(
        userRepository: IUserDataRepository,
        block: suspend () -> T
    ): T {
        return try {
            block()
        } catch (e: AccessTokenExpired) {
            wrapBackendExceptions(userRepository) { userRepository.refreshUserAccessToken() }
            if (userRepository.getUserAccessToken() != null)
                return wrapBackendExceptions(userRepository) { block() }
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}