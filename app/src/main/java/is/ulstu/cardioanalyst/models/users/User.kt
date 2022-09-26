package `is`.ulstu.cardioanalyst.models.users

data class User(
    var token: String?,
    var firstName: String,
    var lastName: String,
    var middleName: String,
    var region: String
)