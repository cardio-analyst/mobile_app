package com.example.data.repositories.users.sources

import com.example.data.repositories.users.sources.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsersApi {
    @POST("auth/signIn")
    suspend fun signIn(@Body body: UserSingInRequestDataEntity): UserSignInResponseDataEntity

    @POST("auth/signUp")
    suspend fun signUp(@Body body: UserSingUpRequestDataEntity): UserSignUpResponseDataEntity

    @GET("profile/info")
    suspend fun getUserInfo(): UserInfoResponseDataEntity

    @PUT("profile/edit")
    suspend fun setUserInfo(@Body body: UserInfoRequestDataEntity): UserInfoResponseDataEntity

    @POST("auth/refreshTokens")
    suspend fun refreshTokens(@Body body: UserRefreshTokensRequestEntity): UserSignInResponseDataEntity
}