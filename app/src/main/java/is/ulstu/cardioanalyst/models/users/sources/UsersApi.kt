package `is`.ulstu.cardioanalyst.models.users.sources

import `is`.ulstu.cardioanalyst.models.users.sources.entities.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UsersApi {
    @POST("auth/signIn")
    suspend fun signIn(@Body body: UserSingInRequestEntity): UserSignInResponseEntity

    @POST("auth/signUp")
    suspend fun signUp(@Body body: UserSingUpRequestEntity): UserSignUpResponseEntity

    @GET("profile/info")
    suspend fun getUserInfo(): UserInfoResponseEntity

    @POST("profile/edit")
    suspend fun setUserInfo(@Body body: UserInfoRequestEntity): UserInfoResponseEntity
}