package `is`.ulstu.cardioanalyst.models.users

class UserRAMRepository : IUserRepository {
    private val regionsList: List<String> = listOf("Ульяновск", "Москва", "Питер")
    private val userMap: MutableMap<User, String> = mutableMapOf(
        User(
            "admin@email.com",
            "admin",
            "token",
            "Иван",
            "Иванов",
            "Иванович",
            "01.01.1970",
            "Ульяновск"
        ) to "admin"
    )
    private lateinit var currentUser: User

    private fun getToken() = when (userMap.size) {
        1 -> "token1"
        2 -> "token2"
        3 -> "token3"
        4 -> "token4"
        5 -> "token5"
        else -> throw Exception("To much users")
    }

    private fun getUser(email: String, login: String) =
        userMap.firstNotNullOfOrNull { if (it.key.email == email) it.key else null }
            ?: userMap.firstNotNullOfOrNull { if (it.key.login == login) it.key else null }

    override fun getCurrentUserInfo() = currentUser

    override fun getCurrentUserToken(): String = currentUser.token ?: "no_token"

    override fun getAllAvailableRegions(): List<String> = regionsList

    override fun enterUser(login: String, password: String) {
        val user = getUser(login, login) ?: throw Exception("Пользователя нет в системе")


        if (userMap[user] == password) {
            currentUser = user
        } else {
            throw Exception("Неверно введен пароль")
        }
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
        if (getUser(email, login) != null)
            throw Exception("Пользователь с такой почтой или логином уже существует")

        userMap[User(
            email,
            login,
            getToken(),
            firstName,
            lastName,
            middleName,
            birthDate,
            region
        )] = password
    }

    override fun changeUserParams(user: User, password: String?) {
        val currentUserPassword =
            userMap[currentUser] ?: throw Exception("Пользователя нет в системе")
        userMap.remove(currentUser)
        if (getUser(user.email, user.login) != null) {
            userMap[currentUser] = currentUserPassword
            throw Exception("Пользователь с такой почтой или логином уже существует")
        }
        userMap[user] = password ?: currentUserPassword
        currentUser = user
    }
}