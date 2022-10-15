package `is`.ulstu.cardioanalyst.models.users

import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserInfoResponseEntity
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserSignUpResponseEntity
import `is`.ulstu.foundation.model.Repository

interface IUserRepository : Repository {
    /**
     * Get user information
     */
    suspend fun getCurrentUserInfo(): UserInfoResponseEntity

    /**
     * Get all available regions
     */
    suspend fun getAllAvailableRegions(): List<String>

    /**
     * Enter user by [loginOrEmail] and [password]
     */
    suspend fun singInUser(loginOrEmail: String, password: String)

    /**
     * Logout current user
     */
    suspend fun logoutUser()

    /**
     * Register new user
     */
    suspend fun signUpUser(
        login: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    ): UserSignUpResponseEntity

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
    ): UserInfoResponseEntity
}