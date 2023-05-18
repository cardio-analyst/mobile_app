package com.example.data

import com.example.common.AccessTokenExpired
import com.example.data.repositories.users.IUserDataDataRepository

/**
 * Base interface for all repositories
 */
interface DataRepository {

    suspend fun <T> wrapBackendExceptions(
        userRepository: IUserDataDataRepository,
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