package `is`.ulstu.cardioanalyst.models.users.sources

import `is`.ulstu.cardioanalyst.app.BackendExceptions
import `is`.ulstu.cardioanalyst.app.ConnectionException
import `is`.ulstu.cardioanalyst.app.ParseBackendResponseException
import `is`.ulstu.cardioanalyst.models.users.sources.entities.*

interface UsersSource {

    /**
     * Execute sign-in request.
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return JWT-token
     */
    suspend fun signIn(userSingInRequestEntity: UserSingInRequestEntity): String

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

}