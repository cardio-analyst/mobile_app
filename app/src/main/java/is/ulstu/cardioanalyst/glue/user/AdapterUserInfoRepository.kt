package `is`.ulstu.cardioanalyst.glue.user

import com.example.common.flows.ResultState
import com.example.data.repositories.users.IUserDataDataRepository
import com.example.profile.domain.UserInfoRepository
import com.example.profile.domain.entities.UserInfoRequestEntity
import com.example.profile.domain.entities.UserInfoResponseEntity
import `is`.ulstu.cardioanalyst.glue.user.mappers.toUserInfoRequestDataEntity
import `is`.ulstu.cardioanalyst.glue.user.mappers.toUserInfoResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterUserInfoRepository @Inject constructor(
    private val userRepository: IUserDataDataRepository,
) : UserInfoRepository {

    override fun getAllAvailableRegions(): List<String> =
        userRepository.getAllAvailableRegions()

    override fun getCurrentUserInfo(): Flow<ResultState<UserInfoResponseEntity>> =
        userRepository.getCurrentUserInfo().map { resultState ->
            resultState.map { userInfoResponseDataEntity ->
                userInfoResponseDataEntity.toUserInfoResponseEntity()
            }
        }

    override fun reloadCurrentUserInfo() = userRepository.reloadCurrentUserInfo()

    override suspend fun changeUserParams(userInfoRequestEntity: UserInfoRequestEntity) =
        userRepository.changeUserParams(userInfoRequestEntity.toUserInfoRequestDataEntity())

    override fun logoutUser() = userRepository.logoutUser()

}