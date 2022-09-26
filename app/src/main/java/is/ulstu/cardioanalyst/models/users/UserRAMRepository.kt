package `is`.ulstu.cardioanalyst.models.users

class UserRAMRepository : IUserRepository {
    private val loginPasswordMap: MutableMap<String, Pair<String, String>> =
        mutableMapOf("admin" to ("admin" to "token"))
    private val userList: MutableList<User> =
        mutableListOf(
            User(
                "token",
                "Иван",
                "Иванов",
                "Иванович",
                "Ульяновск"
            ),
        )
    private lateinit var currentUser: User

    private fun getToken() = when (userList.size) {
        1 -> "token1"
        2 -> "token2"
        3 -> "token3"
        4 -> "token4"
        5 -> "token5"
        else -> throw Exception("To much users")
    }

    override fun getCurrentUserInfo() = currentUser

    override fun getCurrentUserToken(): String = currentUser.token ?: "no_token"

    override fun enterUser(login: String, password: String) {
        if (loginPasswordMap[login]?.first == password) {
            currentUser = userList.find { it.token == loginPasswordMap[login]?.second }
                ?: throw Exception("Something wrong, try later")
        } else {
            throw Exception("Incorrect user password")
        }
    }

    override fun registerNewUser(
        login: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        region: String
    ) {
        loginPasswordMap[login] = password to getToken()
        userList.add(User(loginPasswordMap[login]?.second, firstName, lastName, middleName, region))
    }
}