package `is`.ulstu.cardioanalyst.models.lifestyle

import `is`.ulstu.cardioanalyst.models.lifestyle.sources.LifestyleSource
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LifestyleDBRepository @Inject constructor(
    private val lifestyleSource: LifestyleSource,
    private val userRepository: IUserRepository,
) : ILifestyleRepository {

    private var tempLifestyleMainEntity: LifestyleMainEntity? = null

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

    private suspend fun doGetLifestyle(): LifestyleMainEntity =
        wrapBackendExceptions(userRepository) {
            lifestyleSource.getUserLifestyle()
        }

    override fun reloadGetLifestyleUserRequest() {
        lifestyleLazyFlowSubject.reloadAll()
    }


    override fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): Flow<Result<LifestyleMainEntity>> =
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