package `is`.ulstu.cardioanalyst.models.users.sources.entities

data class UserSignInResponseEntity(
    val accessToken: String,
    val refreshToken: String,
)