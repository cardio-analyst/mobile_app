package `is`.ulstu.cardioanalyst.models.users

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.app.UserSessionExpired
import `is`.ulstu.cardioanalyst.models.users.sources.entities.*
import `is`.ulstu.foundation.utils.LazyFlowSubject

class UserDBRepository : IUserRepository {
    private val usersSource = Singletons.usersSource
    private val appSettings = Singletons.appSettings
    private val regionsList: List<String> = listOf("Ульяновск", "Москва", "Питер")

    // --- Lazy Repository Flows for observers

    private val userLazyFlowSubject = LazyFlowSubject<Unit, UserInfoResponseEntity> {
        doGetCurrentUserInfo()
    }

    private val userSignInLazyFlowSubject =
        LazyFlowSubject<UserSingInRequestEntity, Unit> { userSingInRequestEntity ->
            doSignInUser(userSingInRequestEntity.loginOrEmail, userSingInRequestEntity.password)
        }

    private val userSignUpLazyFlowSubject =
        LazyFlowSubject<UserSingUpRequestEntity, UserSignUpResponseEntity> { userSingUpRequestEntity ->
            doSignUpUser(userSingUpRequestEntity)
        }

    override fun getAllAvailableRegions(): List<String> = regionsList


    override fun getCurrentUserInfo() = userLazyFlowSubject.listen(Unit)

    private suspend fun doGetCurrentUserInfo(): UserInfoResponseEntity = wrapBackendExceptions {
        usersSource.getUserInfo()
    }

    override fun reloadCurrentUserInfo() {
        userLazyFlowSubject.reloadAll()
    }


    override fun signInUser(loginOrEmail: String, password: String) =
        userSignInLazyFlowSubject.listen(UserSingInRequestEntity(loginOrEmail, password))

    private suspend fun doSignInUser(loginOrEmail: String, password: String) {
        val result = usersSource.signIn(UserSingInRequestEntity(loginOrEmail, password))
        appSettings.setUserAccountAccessToken(result.accessToken)
        appSettings.setCurrentRefreshToken(result.refreshToken)
    }

    override fun reloadSignInUserRequest(loginOrEmail: String, password: String) {
        userSignInLazyFlowSubject.reloadArgument(UserSingInRequestEntity(loginOrEmail, password))
    }


    override fun signUpUser(
        login: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    ) = userSignUpLazyFlowSubject.listen(
        UserSingUpRequestEntity(
            firstName,
            lastName,
            middleName,
            birthDate,
            region,
            email,
            login,
            password
        )
    )

    private suspend fun doSignUpUser(userSingUpRequestEntity: UserSingUpRequestEntity): UserSignUpResponseEntity {
        val result = usersSource.signUp(userSingUpRequestEntity)
        if (result.result != "Registered")
            throw Exception("Server error")
        return result
    }

    override fun reloadSignUpUserRequest(
        login: String,
        email: String,
        password: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String
    ) {
        userSignUpLazyFlowSubject.reloadArgument(
            UserSingUpRequestEntity(
                firstName,
                lastName,
                middleName,
                birthDate,
                region,
                email,
                login,
                password
            )
        )
    }


    override suspend fun refreshUserAccessToken() {
        val refreshToken =
            Singletons.appSettings.getCurrentRefreshToken() ?: throw UserSessionExpired()
        val result = usersSource.refreshTokens(UserRefreshTokensRequestEntity(refreshToken))
        appSettings.setUserAccountAccessToken(result.accessToken)
        appSettings.setCurrentRefreshToken(result.refreshToken)
    }

    override suspend fun logoutUser() =
        with(appSettings) { setUserAccountAccessToken(null); setCurrentRefreshToken(null) }

    override suspend fun changeUserParams(
        login: String,
        email: String,
        firstName: String,
        lastName: String,
        middleName: String,
        birthDate: String,
        region: String,
        password: String
    ): Unit = wrapBackendExceptions {
        usersSource.setUserInfo(
            UserInfoRequestEntity(
                email, login, firstName, lastName, middleName, birthDate, region, password
            )
        )
        reloadCurrentUserInfo()
    }
}