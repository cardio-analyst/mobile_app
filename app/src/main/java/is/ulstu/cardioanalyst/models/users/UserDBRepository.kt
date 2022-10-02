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
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    ) {
        TODO("Not yet implemented")
    }

    override fun changeUserParams(user: User, password: String?) {
        TODO("Not yet implemented")
    }
}