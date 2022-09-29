package `is`.ulstu.cardioanalyst.models.users

data class User(
    var email: String?,
    var login: String?,
    var token: String?,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var birthDate: String,
    var region: String,
)