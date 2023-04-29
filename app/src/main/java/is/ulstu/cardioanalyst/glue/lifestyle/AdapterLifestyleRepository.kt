package `is`.ulstu.cardioanalyst.glue.lifestyle

import com.example.common.flows.ResultState
import com.example.data.repositories.lifestyle.ILifestyleDataRepository
import com.example.lifestyle.domain.LifestyleRepository
import com.example.lifestyle.domain.entities.LifestyleEntity
import `is`.ulstu.cardioanalyst.glue.lifestyle.mappers.toLifestyleDataEntity
import `is`.ulstu.cardioanalyst.glue.lifestyle.mappers.toLifestyleEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterLifestyleRepository @Inject constructor(
    private val lifestyleDataRepository: ILifestyleDataRepository,
) : LifestyleRepository {
    override fun getUserLifestyle(): Flow<ResultState<LifestyleEntity>> =
        lifestyleDataRepository.getUserLifestyle().map { resultState ->
            resultState.map { lifestyleDataEntity ->
                lifestyleDataEntity.toLifestyleEntity()
            }
        }

    override fun setUserLifestyle(lifestyleEntity: LifestyleEntity): Flow<ResultState<LifestyleEntity>> =
        lifestyleDataRepository.setUserLifestyle(lifestyleEntity.toLifestyleDataEntity())
            .map { resultState ->
                resultState.map { lifestyleDataEntity ->
                    lifestyleDataEntity.toLifestyleEntity()
                }
            }

    override fun reloadGetLifestyleUserRequest() =
        lifestyleDataRepository.reloadGetLifestyleUserRequest()

    override fun reloadSetLifestyleUserRequest(lifestyleEntity: LifestyleEntity) =
        lifestyleDataRepository.reloadSetLifestyleUserRequest(lifestyleEntity.toLifestyleDataEntity())
}