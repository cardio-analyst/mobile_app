package `is`.ulstu.cardioanalyst.models.users

class UserDBRepository : IUserRepository {
    override fun getCurrentUserInfo(): User {
        TODO("Not yet implemented")
    }

    override fun getCurrentUserToken(): String {
        TODO("Not yet implemented")
    }

    override fun getAllAvailableRegions(): List<String> {
        TODO("Not yet implemented")
    }

    override fun enterUser(login: String, password: String) {
        TODO("Not yet implemented")
    }

    override fun registerNewUser(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        region: String
    ) {
        TODO("Not yet implemented")
    }
}