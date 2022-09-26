package `is`.ulstu.cardioanalyst.models.users

import `is`.ulstu.foundation.model.Repository

interface IUserRepository : Repository {
    /**
     * Get user information
     */
    fun getCurrentUserInfo(): User

    /**
     * Get user token for access database
     */
    fun getCurrentUserToken(): String

    /**
     * Enter user by [login] and [password]
     */
    fun enterUser(login: String, password: String)

    /**
     * Register new user
     */
    fun registerNewUser(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        region: String
    )
}