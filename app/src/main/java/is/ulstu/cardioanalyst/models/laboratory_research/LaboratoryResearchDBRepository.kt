package `is`.ulstu.cardioanalyst.models.laboratory_research

import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.LaboratoryResearchSource
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaboratoryResearchDBRepository @Inject constructor(
    private val laboratoryResearchSource: LaboratoryResearchSource,
    private val userRepository: IUserRepository,
) : ILaboratoryResearchRepository {

    // --- Lazy Repository Flows for observers

    private val laboratoryResearchesLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetLaboratoryResearchResponseEntity>> {
            doGetLaboratoryResearches()
        }

    private val laboratoryResearchCreateLazyFlowSubject =
        LazyFlowSubject<CreateLaboratoryResearchRequestEntity, CreateLaboratoryResearchResponseEntity>
        { createLaboratoryResearchRequestEntity ->
            doCreateLaboratoryResearches(createLaboratoryResearchRequestEntity)
        }

    private val laboratoryResearchUpdateLazyFlowSubject =
        LazyFlowSubject<UpdateLaboratoryResearchIdEntity, UpdateLaboratoryResearchResponseEntity>
        { updateLaboratoryResearchRequestIdEntity ->
            doUpdateLaboratoryResearch(updateLaboratoryResearchRequestIdEntity)
        }


    override fun getLaboratoryResearches(): Flow<Result<List<GetLaboratoryResearchResponseEntity>>> =
        laboratoryResearchesLazyFlowSubject.listen(Unit)

    private suspend fun doGetLaboratoryResearches(): List<GetLaboratoryResearchResponseEntity> =
        wrapBackendExceptions(userRepository) {
            laboratoryResearchSource.getLaboratoryResearches()
        }

    override fun reloadGetLaboratoryResearches() {
        laboratoryResearchesLazyFlowSubject.reloadAll()
    }


    override fun createLaboratoryResearch(
        createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity
    ): Flow<Result<CreateLaboratoryResearchResponseEntity>> =
        laboratoryResearchCreateLazyFlowSubject.listen(createLaboratoryResearchRequestEntity)


    private suspend fun doCreateLaboratoryResearches(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity): CreateLaboratoryResearchResponseEntity =
        wrapBackendExceptions(userRepository) {
            laboratoryResearchSource.createLaboratoryResearch(createLaboratoryResearchRequestEntity)
        }

    override fun reloadCreateLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity) {
        laboratoryResearchCreateLazyFlowSubject.reloadArgument(createLaboratoryResearchRequestEntity)
    }


    override fun updateLaboratoryResearch(
        updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity
    ): Flow<Result<UpdateLaboratoryResearchResponseEntity>> =
        laboratoryResearchUpdateLazyFlowSubject.listen(updateLaboratoryResearchIdEntity)

    private suspend fun doUpdateLaboratoryResearch(updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity)
            : UpdateLaboratoryResearchResponseEntity =
        wrapBackendExceptions(userRepository) {
            laboratoryResearchSource.updateLaboratoryResearch(
                laboratoryResearchId = updateLaboratoryResearchIdEntity.laboratoryResearchId,
                updateLaboratoryResearchRequestEntity = UpdateLaboratoryResearchRequestEntity(
                    highDensityCholesterol = updateLaboratoryResearchIdEntity.highDensityCholesterol,
                    lowDensityCholesterol = updateLaboratoryResearchIdEntity.lowDensityCholesterol,
                    triglycerides = updateLaboratoryResearchIdEntity.triglycerides,
                    lipoprotein = updateLaboratoryResearchIdEntity.lipoprotein,
                    highlySensitiveCReactiveProtein = updateLaboratoryResearchIdEntity.highlySensitiveCReactiveProtein,
                    atherogenicityCoefficient = updateLaboratoryResearchIdEntity.atherogenicityCoefficient,
                    creatinine = updateLaboratoryResearchIdEntity.creatinine,
                    atheroscleroticPlaquesPresence = updateLaboratoryResearchIdEntity.atheroscleroticPlaquesPresence,
                )
            )
        }

    override fun reloadUpdateLaboratoryResearch(
        updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity
    ) {
        laboratoryResearchUpdateLazyFlowSubject.reloadArgument(updateLaboratoryResearchIdEntity)
    }
}