package `is`.ulstu.cardioanalyst.app

import `is`.ulstu.cardioanalyst.R


// --- Base App Exception

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
    constructor(message: String, cause: Throwable) : super(message, cause)
}


// --- Different Exceptions

class ConnectionException(cause: Throwable) :
    AppException(Singletons.getString(R.string.app_logic_ex_connection_ex), cause = cause)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

class ErrorResponseBody(
    val error: String,
    val description: String,
)


// --- Backend Exceptions Based in Sealed class

sealed class BackendExceptions(val description: String = "") : AppException() {
    companion object {
        fun initBackendException(errorResponseBody: ErrorResponseBody): BackendExceptions {

            return when (errorResponseBody.error) {
                "AlreadyRegisteredWithLogin" -> AlreadyRegisteredWithLogin(
                    description = errorResponseBody.description
                )
                "RefreshTokenExpired", "WrongRefreshToken" -> RefreshTokenExpired()
                "AccessTokenExpired", "WrongAccessToken" -> AccessTokenExpired()
                else -> {
                    BackendException(
                        error = errorResponseBody.error,
                        description = errorResponseBody.description
                    )
                }
            }
        }
    }
}

class BackendException(
    val error: String,
    description: String
) : BackendExceptions(description)

class AlreadyRegisteredWithLogin(description: String) : BackendExceptions(description)

class RefreshTokenExpired : BackendExceptions()

class AccessTokenExpired : BackendExceptions()


// --- User Input Exceptions Based in Sealed class

sealed class InputExceptions(val description: String) : AppException()

class IncorrectEmailException() :
    InputExceptions(Singletons.getString(R.string.in_ex_incorrect_email))

class IncorrectFullNameException() :
    InputExceptions(Singletons.getString(R.string.in_ex_incorrect_full_name))

class IncorrectBirthDateException() :
    InputExceptions(Singletons.getString(R.string.in_ex_incorrect_birth_date))

class IncorrectRegionException() :
    InputExceptions(Singletons.getString(R.string.in_ex_incorrect_region))

class IncorrectPasswordException() :
    InputExceptions(Singletons.getString(R.string.in_ex_incorrect_password))


// --- User Input Exceptions Based in Sealed class

sealed class AppLogicExceptions(description: String) : AppException(message = description)

class UserSessionExpired :
    AppLogicExceptions(Singletons.getString(R.string.app_logic_ex_user_session_expired))