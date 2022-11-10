package `is`.ulstu.cardioanalyst.models.lifestyle

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow

class LifestyleDBRepository : ILifestyleRepository {

    private val lifestyleSource = Singletons.lifestyleSource

    // --- Lazy Repository Flows for observers

    private val lifestyleLazyFlowSubject = LazyFlowSubject<Unit, LifestyleMainEntity> {
        doGetLifestyle()
    }

    private val lifestyleSaveLazyFlowSubject =
        LazyFlowSubject<LifestyleMainEntity, LifestyleMainEntity> { lifestyleMainEntity ->
            doSetUserLifestyle(lifestyleMainEntity)
        }


    override fun getUserLifestyle(): Flow<Result<LifestyleMainEntity>> =
        lifestyleLazyFlowSubject.listen(Unit)

    private suspend fun doGetLifestyle(): LifestyleMainEntity = wrapBackendExceptions {
        lifestyleSource.getUserLifestyle()
    }

    override fun reloadGetLifestyleUserRequest() {
        lifestyleLazyFlowSubject.reloadAll()
    }


    override fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): Flow<Result<LifestyleMainEntity>> =
        lifestyleSaveLazyFlowSubject.listen(lifestyleMainEntity)

    private suspend fun doSetUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): LifestyleMainEntity =
        wrapBackendExceptions { lifestyleSource.setUserLifestyle(lifestyleMainEntity) }

    override fun reloadSetLifestyleUserRequest(lifestyleMainEntity: LifestyleMainEntity) {
        lifestyleSaveLazyFlowSubject.reloadArgument(lifestyleMainEntity)
    }
}