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

    override suspend fun signIn(userSingInRequestDataEntity: UserSingInRequestDataEntity): UserSignInResponseDataEntity =
        wrapRetrofitExceptions { usersApi.signIn(userSingInRequestDataEntity) }


    override suspend fun signUp(userSingUpRequestDataEntity: UserSingUpRequestDataEntity): UserSignUpResponseDataEntity =
        wrapRetrofitExceptions { usersApi.signUp(userSingUpRequestDataEntity) }


    override suspend fun getUserInfo(): UserInfoResponseEntity =
        wrapRetrofitExceptions { usersApi.getUserInfo() }

    override suspend fun setUserInfo(userInfoRequestEntity: UserInfoRequestEntity): UserInfoResponseEntity =
        wrapRetrofitExceptions { usersApi.setUserInfo(userInfoRequestEntity) }

    override suspend fun refreshTokens(userRefreshTokensRequestEntity: UserRefreshTokensRequestEntity): UserSignInResponseDataEntity =
        wrapRetrofitExceptions { usersApi.refreshTokens(userRefreshTokensRequestEntity) }
}