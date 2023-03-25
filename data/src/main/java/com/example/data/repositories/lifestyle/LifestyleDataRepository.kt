package com.example.data.repositories.lifestyle

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.lifestyle.sources.LifestyleSource
import com.example.data.repositories.lifestyle.sources.entities.LifestyleMainEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LifestyleDataRepository @Inject constructor(
    private val lifestyleSource: LifestyleSource,
    private val userRepository: IUserDataRepository,
) : ILifestyleDataRepository {

    private var tempLifestyleMainEntity: LifestyleMainEntity? = null

    // --- Lazy Repository Flows for observers

    private val lifestyleLazyFlowSubject = LazyFlowSubject<Unit, LifestyleMainEntity> {
        doGetLifestyle()
    }

    private val lifestyleSaveLazyFlowSubject =
        LazyFlowSubject<LifestyleMainEntity, LifestyleMainEntity> { lifestyleMainEntity ->
            doSetUserLifestyle(lifestyleMainEntity)
        }


    override fun getUserLifestyle(): Flow<ResultState<LifestyleMainEntity>> =
        lifestyleLazyFlowSubject.listen(Unit)

    private suspend fun doGetLifestyle(): LifestyleMainEntity =
        wrapBackendExceptions(userRepository) {
            lifestyleSource.getUserLifestyle()
        }

    override fun reloadGetLifestyleUserRequest() {
        lifestyleLazyFlowSubject.reloadAll()
    }


    override fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): Flow<ResultState<LifestyleMainEntity>> =
        lifestyleSaveLazyFlowSubject.listen(lifestyleMainEntity)

    private suspend fun doSetUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): LifestyleMainEntity =
        wrapBackendExceptions(userRepository) {
            lifestyleSource.setUserLifestyle(
                lifestyleMainEntity
            )
        }

    override fun reloadSetLifestyleUserRequest(lifestyleMainEntity: LifestyleMainEntity) {
        lifestyleSaveLazyFlowSubject.reloadArgument(lifestyleMainEntity)
    }

    override fun setCurrentChanges(lifestyleMainEntity: LifestyleMainEntity?) {
        tempLifestyleMainEntity = lifestyleMainEntity?.copy()
    }

    override fun getCurrentChanges(): LifestyleMainEntity? {
        return tempLifestyleMainEntity
    }

}