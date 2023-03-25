package com.example.data.repositories.users.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.users.sources.entities.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitUsersSource @Inject constructor(
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