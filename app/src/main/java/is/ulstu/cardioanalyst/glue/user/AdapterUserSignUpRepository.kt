package `is`.ulstu.cardioanalyst.glue.user

import com.example.common.flows.ResultState
import com.example.data.repositories.users.IUserDataRepository
import com.example.registration.domain.UserSignUpRepository
import com.example.registration.domain.entities.UserSignUpResponseEntity
import com.example.registration.domain.entities.UserSingUpRequestEntity
import `is`.ulstu.cardioanalyst.glue.user.mappers.toUserSignUpRequestDataEntity
import `is`.ulstu.cardioanalyst.glue.user.mappers.toUserSignUpResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterUserSignUpRepository @Inject constructor(
    private val userRepository: IUserDataRepository,
) : UserSignUpRepository {

    override fun signInUser(loginOrEmail: String, password: String): Flow<ResultState<Unit>> =
        userRepository.signInUser(loginOrEmail, password)

    override fun reloadSignInUserRequest(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)

    override fun getAllAvailableRegions(): List<String> =
        userRepository.getAllAvailableRegions()

    override fun signUpUser(
        userSingUpRequestEntity: UserSingUpRequestEntity
    ): Flow<ResultState<UserSignUpResponseEntity>> =
        userRepository.signUpUser(userSingUpRequestEntity.toUserSignUpRequestDataEntity())
            .map { resultState ->
                resultState.map { userSignUpResponseDataEntity ->
                    userSignUpResponseDataEntity.toUserSignUpResponseEntity()
                }
            }

    override fun reloadSignUpUserRequest(userSingUpRequestEntity: UserSingUpRequestEntity) =
        userRepository.reloadSignUpUserRequest(userSingUpRequestEntity.toUserSignUpRequestDataEntity())

}