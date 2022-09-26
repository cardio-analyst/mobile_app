package `is`.ulstu.cardioanalyst.models.users

class UserRAMRepository : IUserRepository {
    private val currentUser: User = User("token", "Иван", "Иванов", "Иванович", "Ульяновск")

    override fun getCurrentUserInfo() = currentUser

    override fun getCurrentUserToken(): String = currentUser.token ?: "no_token"
}