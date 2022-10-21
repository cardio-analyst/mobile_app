package `is`.ulstu.cardioanalyst.models.users

import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserInfoResponseEntity
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserSignUpResponseEntity
import `is`.ulstu.foundation.model.Repository
import `is`.ulstu.foundation.model.Result
import kotlinx.coroutines.flow.Flow

interface IUserRepository : Repository {

    /**
     * Get user information
     */
    fun getCurrentUserInfo(): Flow<Result<UserInfoResponseEntity>>

    /**
     * Reload user information
     */
    fun reloadCurrentUserInfo()

    /**
     * Get all available regions
     */
    fun getAllAvailableRegions(): List<String>

    /**
     * Refresh access token for user account session
     */
    suspend fun refreshUserAccessToken()

    /**
     * Enter user by [loginOrEmail] and [password]
     */
    fun signInUser(loginOrEmail: String, password: String): Flow<Result<Unit>>

    /**
     * Reload user sign in request in case error
     */
    fun reloadSignInUserRequest(loginOrEmail: String, password: String)

    /**
     * Logout current user
     */
    suspend fun logoutUser()

    /**
     * Register new user
     */
    fun signUpUser(
        login: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    ): Flow<Result<UserSignUpResponseEntity>>

    /**
     * Reload user sign in request in case error
     */
    fun reloadSignUpUserRequest(
        login: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    )

    /**
     * Change user params
     */
    suspend fun changeUserParams(
        login: String,
        email: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String,
        password: String
    )
}