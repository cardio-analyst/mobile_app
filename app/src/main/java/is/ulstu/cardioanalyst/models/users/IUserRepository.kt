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
}