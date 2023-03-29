package `is`.ulstu.cardioanalyst.glue.user.mappers

import com.example.data.repositories.users.sources.entities.UserSignUpResponseDataEntity
import com.example.data.repositories.users.sources.entities.UserSingUpRequestDataEntity
import com.example.registration.domain.entities.UserSignUpResponseEntity
import com.example.registration.domain.entities.UserSingUpRequestEntity

fun UserSingUpRequestEntity.toUserSignUpRequestDataEntity() = UserSingUpRequestDataEntity(
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    birthDate = birthDate,
    region = region,
    email = email,
    login = login,
    password = password,
)

fun UserSignUpResponseDataEntity.toUserSignUpResponseEntity() = UserSignUpResponseEntity(
    result = result,
)