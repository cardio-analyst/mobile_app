package `is`.ulstu.cardioanalyst.models.users.sources.entities

data class UserInfoRequestEntity(
    val email: String,
    val login: String,
    val firstName: String,
    val lastName: String,
    val middleName: String,
    val birthDate: String,
    val region: String,
    val password: String,
)