package `is`.ulstu.cardioanalyst.models.users

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.users.sources.entities.*

class UserDBRepository : IUserRepository {

    private val usersSource = Singletons.usersSource
    private val appSettings = Singletons.appSettings
    private val regionsList: List<String> = listOf("Ульяновск", "Москва", "Питер")

    override suspend fun getCurrentUserInfo() = usersSource.getUserInfo()

    override suspend fun getAllAvailableRegions(): List<String> = regionsList

    override suspend fun singInUser(loginOrEmail: String, password: String) {
        val token = usersSource.signIn(UserSingInRequestEntity(loginOrEmail, password))
        appSettings.setCurrentToken(token)
    }

    override suspend fun logoutUser() = appSettings.setCurrentToken(null)

    override suspend fun signUpUser(
        login: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    ): UserSignUpResponseEntity {
        val result = usersSource.signUp(
            UserSingUpRequestEntity(
                firstName, lastName, middleName, birthDate, region, email, login, password
            )
        )
        if (result.result != "Ok")
            throw Exception("Server error")
        return result
    }

    override suspend fun changeUserParams(
        login: String,
        email: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String,
        password: String
    ): UserInfoResponseEntity = usersSource.setUserInfo(
        UserInfoRequestEntity(
            email, login, firstName, lastName, middleName, birthDate, region, password
        )
    )
}