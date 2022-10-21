package `is`.ulstu.cardioanalyst.models.users.sources

import `is`.ulstu.cardioanalyst.models.users.sources.entities.*
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig

class RetrofitUsersSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), UsersSource {

    private val usersApi = retrofit.create(UsersApi::class.java)

    override suspend fun signIn(userSingInRequestEntity: UserSingInRequestEntity): UserSignInResponseEntity =
        wrapRetrofitExceptions { usersApi.signIn(userSingInRequestEntity) }


    override suspend fun signUp(userSingUpRequestEntity: UserSingUpRequestEntity): UserSignUpResponseEntity =
        wrapRetrofitExceptions { usersApi.signUp(userSingUpRequestEntity) }


    override suspend fun getUserInfo(): UserInfoResponseEntity =
        wrapRetrofitExceptions { usersApi.getUserInfo() }

    override suspend fun setUserInfo(userInfoRequestEntity: UserInfoRequestEntity): UserInfoResponseEntity =
        wrapRetrofitExceptions { usersApi.setUserInfo(userInfoRequestEntity) }

    override suspend fun refreshTokens(userRefreshTokensRequestEntity: UserRefreshTokensRequestEntity): UserSignInResponseEntity =
        wrapRetrofitExceptions { usersApi.refreshTokens(userRefreshTokensRequestEntity) }
}