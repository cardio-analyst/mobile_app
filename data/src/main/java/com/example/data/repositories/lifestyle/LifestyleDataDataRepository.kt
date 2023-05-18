package com.example.data.repositories.lifestyle

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.lifestyle.sources.LifestyleSource
import com.example.data.repositories.lifestyle.sources.entities.LifestyleDataEntity
import com.example.data.repositories.users.IUserDataDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LifestyleDataDataRepository @Inject constructor(
    private val lifestyleSource: LifestyleSource,
    private val userRepository: IUserDataDataRepository,
) : ILifestyleDataDataRepository {

    // --- Lazy Repository Flows for observers

    private val lifestyleLazyFlowSubject = LazyFlowSubject<Unit, LifestyleDataEntity> {
        doGetLifestyle()
    }

    private val lifestyleSaveLazyFlowSubject =
        LazyFlowSubject<LifestyleDataEntity, LifestyleDataEntity> { lifestyleMainEntity ->
            doSetUserLifestyle(lifestyleMainEntity)
        }


    override fun getUserLifestyle(): Flow<ResultState<LifestyleDataEntity>> =
        lifestyleLazyFlowSubject.listen(Unit)

    private suspend fun doGetLifestyle(): LifestyleDataEntity =
        wrapBackendExceptions(userRepository) {
            lifestyleSource.getUserLifestyle()
        }

    override fun reloadGetLifestyleUserRequest() {
        lifestyleLazyFlowSubject.reloadAll()
    }


    override fun setUserLifestyle(lifestyleDataEntity: LifestyleDataEntity): Flow<ResultState<LifestyleDataEntity>> =
        lifestyleSaveLazyFlowSubject.listen(lifestyleDataEntity)

    private suspend fun doSetUserLifestyle(lifestyleDataEntity: LifestyleDataEntity): LifestyleDataEntity =
        wrapBackendExceptions(userRepository) {
            lifestyleSource.setUserLifestyle(
                lifestyleDataEntity
            )
        }

    override fun reloadSetLifestyleUserRequest(lifestyleDataEntity: LifestyleDataEntity) {
        lifestyleSaveLazyFlowSubject.reloadArgument(lifestyleDataEntity)
    }

}