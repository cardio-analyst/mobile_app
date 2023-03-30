package `is`.ulstu.cardioanalyst.glue.user.mappers

import com.example.data.repositories.users.sources.entities.UserInfoRequestDataEntity
import com.example.data.repositories.users.sources.entities.UserInfoResponseDataEntity
import com.example.data.repositories.users.sources.entities.UserSignUpResponseDataEntity
import com.example.data.repositories.users.sources.entities.UserSingUpRequestDataEntity
import com.example.profile.domain.entities.UserInfoRequestEntity
import com.example.profile.domain.entities.UserInfoResponseEntity
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

fun UserInfoRequestEntity.toUserInfoRequestDataEntity() = UserInfoRequestDataEntity(
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    birthDate = birthDate,
    region = region,
    email = email,
    login = login,
    password = password,
)

fun UserInfoResponseDataEntity.toUserInfoResponseEntity() = UserInfoResponseEntity(
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    birthDate = birthDate,
    region = region,
    email = email,
    login = login,
)