package `is`.ulstu.cardioanalyst.models.users.sources.entities

data class UserSingInRequestEntity(
    val loginOrEmail: String,
    val password: String
)