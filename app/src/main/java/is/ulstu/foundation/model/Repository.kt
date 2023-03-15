package `is`.ulstu.foundation.model

import com.example.common.AccessTokenExpired
import `is`.ulstu.cardioanalyst.models.users.IUserRepository

/**
 * Base interface for all repositories
 */
interface Repository {

    suspend fun <T> wrapBackendExceptions(
        userRepository: IUserRepository,
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