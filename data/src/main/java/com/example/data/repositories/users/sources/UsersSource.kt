package com.example.data.repositories.users.sources

import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.ParseBackendResponseException
import com.example.data.repositories.users.sources.entities.*

interface UsersSource {

    /**
     * Execute sign-in request.
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UserSignInResponseDataEntity] which contains [accessToken] and [refreshToken] tokens
     */
    suspend fun signIn(userSingInRequestDataEntity: UserSingInRequestDataEntity): UserSignInResponseDataEntity

    /**
     * Create a new user account.
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UserSignUpResponseDataEntity]
     */
    suspend fun signUp(userSingUpRequestDataEntity: UserSingUpRequestDataEntity): UserSignUpResponseDataEntity

    /**
     * Get the user info of the current signed-in user.
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UserInfoResponseEntity]
     */
    suspend fun getUserInfo(): UserInfoResponseEntity

    /**
     * Change the user info of the current signed-in user.
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UserInfoResponseEntity]
     */
    suspend fun setUserInfo(userInfoRequestEntity: UserInfoRequestEntity): UserInfoResponseEntity

    /**
     * Refresh the user's tokens
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UserInfoResponseEntity] which contains [accessToken] and [refreshToken] tokens
     */
    suspend fun refreshTokens(userRefreshTokensRequestEntity: UserRefreshTokensRequestEntity): UserSignInResponseDataEntity

}