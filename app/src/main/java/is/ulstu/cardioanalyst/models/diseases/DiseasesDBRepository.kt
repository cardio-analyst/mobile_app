package `is`.ulstu.cardioanalyst.models.diseases

import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiseasesDBRepository @Inject constructor(
    private val diseasesSource: DiseasesSource,
    private val userRepository: IUserRepository,
) : IDiseasesRepository {

    // --- Lazy Repository Flows for observers

    private val diseasesLazyFlowSubject = LazyFlowSubject<Unit, DiseasesMainEntity> {
        doGetDiseases()
    }

    private val diseasesSaveLazyFlowSubject =
        LazyFlowSubject<DiseasesMainEntity, DiseasesMainEntity> { diseasesMainEntity ->
            doSetUserDiseases(diseasesMainEntity)
        }


    override fun getUserDiseases(): Flow<Result<DiseasesMainEntity>> =
        diseasesLazyFlowSubject.listen(Unit)

    private suspend fun doGetDiseases(): DiseasesMainEntity = wrapBackendExceptions(userRepository) {
        diseasesSource.getUserDiseases()
    }

    override fun reloadGetDiseasesUserRequest() {
        diseasesLazyFlowSubject.reloadAll()
    }


    override fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): Flow<Result<DiseasesMainEntity>> =
        diseasesSaveLazyFlowSubject.listen(diseasesMainEntity)

    private suspend fun doSetUserDiseases(diseasesMainEntity: DiseasesMainEntity): DiseasesMainEntity =
        wrapBackendExceptions(userRepository) { diseasesSource.setUserDiseases(diseasesMainEntity) }

    override fun reloadSetDiseasesUserRequest(diseasesMainEntity: DiseasesMainEntity) {
        diseasesSaveLazyFlowSubject.reloadArgument(diseasesMainEntity)
    }

}