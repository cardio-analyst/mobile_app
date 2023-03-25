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
     * @return [UserSignInResponseEntity] which contains [accessToken] and [refreshToken] tokens
     */
    suspend fun signIn(userSingInRequestEntity: UserSingInRequestEntity): UserSignInResponseEntity

    /**
     * Create a new user account.
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [UserSignUpResponseEntity]
     */
    suspend fun signUp(userSingUpRequestEntity: UserSingUpRequestEntity): UserSignUpResponseEntity

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
    suspend fun refreshTokens(userRefreshTokensRequestEntity: UserRefreshTokensRequestEntity): UserSignInResponseEntity

}