package `is`.ulstu.cardioanalyst.models.diseases

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow

class DiseasesDBRepository : IDiseasesRepository {

    private val diseasesSource = Singletons.diseasesSource

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

    private suspend fun doGetDiseases(): DiseasesMainEntity = wrapBackendExceptions {
        diseasesSource.getUserDiseases()
    }

    override fun reloadGetDiseasesUserRequest() {
        diseasesLazyFlowSubject.reloadAll()
    }


    override fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): Flow<Result<DiseasesMainEntity>> =
        diseasesSaveLazyFlowSubject.listen(diseasesMainEntity)

    private suspend fun doSetUserDiseases(diseasesMainEntity: DiseasesMainEntity): DiseasesMainEntity =
        wrapBackendExceptions { diseasesSource.setUserDiseases(diseasesMainEntity) }

    override fun reloadSetDiseasesUserRequest(diseasesMainEntity: DiseasesMainEntity) {
        diseasesSaveLazyFlowSubject.reloadArgument(diseasesMainEntity)
    }

}