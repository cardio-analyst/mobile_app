package `is`.ulstu.cardioanalyst.app

import `is`.ulstu.cardioanalyst.R


// --- Base App Exception

open class AppException : RuntimeException {
    constructor() : super()
    constructor(message: String) : super(message)
    constructor(cause: Throwable) : super(cause)
}


// --- Different Exceptions

class ConnectionException(cause: Throwable) : AppException(cause = cause)

class ParseBackendResponseException(
    cause: Throwable
) : AppException(cause = cause)

class ErrorResponseBody(
    val error: String,
    val description: String,
)


// --- Backend Exceptions Based in Sealed class

sealed class BackendExceptions : AppException() {
    companion object {
        fun initBackendException(errorResponseBody: ErrorResponseBody) = when (errorResponseBody.error) {
            "AlreadyRegisteredWithLogin" -> AlreadyRegisteredWithLogin(errorResponseBody.description)
            else -> {
                BackendException(
                    error = errorResponseBody.error,
                    description = errorResponseBody.description
                )
            }
        }
    }
}

class BackendException(
    val error: String,
    val description: String
) : BackendExceptions()

class AlreadyRegisteredWithLogin(val description: String) : BackendExceptions()


// --- User Input Exceptions Based in Sealed class

sealed class InputExceptions(val description: String) : AppException()

class IncorrectEmailException() : InputExceptions(Singletons.getString(R.string.in_ex_incorrect_email))

class IncorrectFullNameException() : InputExceptions(Singletons.getString(R.string.in_ex_incorrect_full_name))

class IncorrectBirthDateException() : InputExceptions(Singletons.getString(R.string.in_ex_incorrect_birth_date))

class IncorrectRegionException() : InputExceptions(Singletons.getString(R.string.in_ex_incorrect_region))

class IncorrectPasswordException() : InputExceptions(Singletons.getString(R.string.in_ex_incorrect_password))
