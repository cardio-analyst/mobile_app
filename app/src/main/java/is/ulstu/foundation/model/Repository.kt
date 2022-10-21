package `is`.ulstu.foundation.model

import `is`.ulstu.cardioanalyst.app.AccessTokenExpired
import `is`.ulstu.cardioanalyst.app.Singletons

/**
 * Base interface for all repositories
 */
interface Repository {
    suspend fun <T> wrapBackendExceptions(block: suspend () -> T): T {
        return try {
            block()
        } catch (e: AccessTokenExpired) {
            wrapBackendExceptions { Singletons.userRepository.refreshUserAccessToken() }
            if (Singletons.appSettings.getUserAccountAccessToken() != null)
                return wrapBackendExceptions(block)
            throw e
        } catch (e: Exception) {
            throw e
        }
    }
}