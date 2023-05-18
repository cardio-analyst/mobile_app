package `is`.ulstu.cardioanalyst.glue.user

import com.example.authorization.domain.UserSignInRepository
import com.example.common.flows.ResultState
import com.example.data.repositories.users.IUserDataDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterUserSignInRepository @Inject constructor(
    private val userRepository: IUserDataDataRepository,
) : UserSignInRepository {

    override fun signInUser(loginOrEmail: String, password: String): Flow<ResultState<Unit>> =
        userRepository.signInUser(loginOrEmail, password)

    override fun reloadSignInUserRequest(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)
}