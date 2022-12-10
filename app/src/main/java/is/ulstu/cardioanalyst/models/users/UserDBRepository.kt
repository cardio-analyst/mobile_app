package `is`.ulstu.cardioanalyst.models.users

import `is`.ulstu.cardioanalyst.app.UserSessionExpired
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.models.users.sources.UsersSource
import `is`.ulstu.cardioanalyst.models.users.sources.entities.*
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserDBRepository @Inject constructor(
    private val usersSource: UsersSource,
    private val userSettings: UserSettings,
) : IUserRepository {

    private val regionsList: List<String> = listOf(
        "Москва",
        "Санкт-Петербург",
        "Севастополь",
        "Республика Адыгея",
        "Республика Алтай",
        "Республика Башкортостан",
        "Республика Бурятия",
        "Республика Дагестан",
        "Донецкая Народная Республика",
        "Республика Ингушетия",
        "Кабардино-Балкарская Республика",
        "Республика Калмыкия",
        "Карачаево-Черкесская Республика",
        "Республика Карелия",
        "Республика Коми",
        "Республика Крым",
        "Луганская Народная Республика",
        "Республика Марий Эл",
        "Республика Мордовия",
        "Республика Саха (Якутия)",
        "Республика Северная Осетия — Алания",
        "Республика Татарстан",
        "Республика Тыва",
        "Удмуртская Республика",
        "Республика Хакасия",
        "Чеченская Республика",
        "Чувашская Республика — Чувашия",
        "Алтайский край",
        "Забайкальский край",
        "Камчатский край",
        "Краснодарский край",
        "Красноярский край",
        "Пермский край",
        "Приморский край",
        "Ставропольский край",
        "Хабаровский край",
        "Амурская область",
        "Архангельская область",
        "Астраханская область",
        "Белгородская область",
        "Брянская область",
        "Владимирская область",
        "Волгоградская область",
        "Вологодская область",
        "Воронежская область",
        "Запорожская область",
        "Ивановская область",
        "Иркутская область",
        "Калининградская область",
        "Калужская область",
        "Кемеровская область — Кузбасс",
        "Кировская область",
        "Костромская область",
        "Курганская область",
        "Курская область",
        "Ленинградская область",
        "Липецкая область",
        "Магаданская область",
        "Московская область",
        "Мурманская область",
        "Нижегородская область",
        "Новгородская область",
        "Новосибирская область",
        "Омская область",
        "Оренбургская область",
        "Орловская область",
        "Пензенская область",
        "Псковская область",
        "Ростовская область",
        "Рязанская область",
        "Самарская область",
        "Саратовская область",
        "Сахалинская область",
        "Свердловская область",
        "Смоленская область",
        "Тамбовская область",
        "Тверская область",
        "Томская область",
        "Тульская область",
        "Тюменская область",
        "Ульяновская область",
        "Херсонская область",
        "Челябинская область",
        "Ярославская область",
        "Еврейская АО",
        "Ненецкий АО",
        "Ханты-Мансийский АО — Югра",
        "Чукотский АО",
        "Ямало-Ненецкий АО",
    )

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

    private suspend fun doGetCurrentUserInfo(): UserInfoResponseEntity =
        wrapBackendExceptions(this@UserDBRepository) {
            usersSource.getUserInfo()
        }

    override fun reloadCurrentUserInfo() {
        userLazyFlowSubject.reloadAll()
    }


    override fun signInUser(loginOrEmail: String, password: String) =
        userSignInLazyFlowSubject.listen(UserSingInRequestEntity(loginOrEmail, password))

    private suspend fun doSignInUser(loginOrEmail: String, password: String) {
        val result = usersSource.signIn(UserSingInRequestEntity(loginOrEmail, password))
        userSettings.setUserAccountAccessToken(result.accessToken)
        userSettings.setCurrentRefreshToken(result.refreshToken)
    }

    override fun reloadSignInUserRequest(loginOrEmail: String, password: String) {
        userSignInLazyFlowSubject.reloadArgument(UserSingInRequestEntity(loginOrEmail, password))
    }


    override fun signUpUser(userSingUpRequestEntity: UserSingUpRequestEntity): Flow<Result<UserSignUpResponseEntity>> =
        userSignUpLazyFlowSubject.listen(userSingUpRequestEntity)

    private suspend fun doSignUpUser(userSingUpRequestEntity: UserSingUpRequestEntity): UserSignUpResponseEntity {
        val result = usersSource.signUp(userSingUpRequestEntity)
        if (result.result != "Registered")
            throw Exception("Server error")
        return result
    }

    override fun reloadSignUpUserRequest(userSingUpRequestEntity: UserSingUpRequestEntity) {
        userSignUpLazyFlowSubject.reloadArgument(userSingUpRequestEntity)
    }


    override suspend fun getUserAccessToken(): String? = userSettings.getUserAccountAccessToken()

    override suspend fun refreshUserAccessToken() {
        val refreshToken =
            userSettings.getCurrentRefreshToken() ?: throw UserSessionExpired()
        val result = usersSource.refreshTokens(UserRefreshTokensRequestEntity(refreshToken))
        userSettings.setUserAccountAccessToken(result.accessToken)
        userSettings.setCurrentRefreshToken(result.refreshToken)
    }

    override suspend fun logoutUser() =
        with(userSettings) { setUserAccountAccessToken(null); setCurrentRefreshToken(null) }

    override suspend fun changeUserParams(userInfoRequestEntity: UserInfoRequestEntity) =
        wrapBackendExceptions(this@UserDBRepository) {
            usersSource.setUserInfo(userInfoRequestEntity)
            reloadCurrentUserInfo()
        }
}