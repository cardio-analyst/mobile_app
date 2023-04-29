package com.example.data.repositories.laboratory_research

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.laboratory_research.sources.LaboratoryResearchSource
import com.example.data.repositories.laboratory_research.sources.entities.*
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LaboratoryResearchDataRepository @Inject constructor(
    private val laboratoryResearchSource: LaboratoryResearchSource,
    private val userRepository: IUserDataRepository,
) : ILaboratoryResearchDataRepository {

    // --- Lazy Repository Flows for observers

    private val laboratoryResearchesLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetLaboratoryResearchResponseDataEntity>> {
            doGetLaboratoryResearches()
        }

    private val laboratoryResearchCreateLazyFlowSubject =
        LazyFlowSubject<CreateLaboratoryResearchRequestDataEntity, CreateLaboratoryResearchResponseDataEntity>
        { createLaboratoryResearchRequestEntity ->
            doCreateLaboratoryResearches(createLaboratoryResearchRequestEntity)
        }

    private val laboratoryResearchUpdateLazyFlowSubject =
        LazyFlowSubject<UpdateLaboratoryResearchIdDataEntity, UpdateLaboratoryResearchResponseDataEntity>
        { updateLaboratoryResearchRequestIdEntity ->
            doUpdateLaboratoryResearch(updateLaboratoryResearchRequestIdEntity)
        }


    override fun getLaboratoryResearches(): Flow<ResultState<List<GetLaboratoryResearchResponseDataEntity>>> =
        laboratoryResearchesLazyFlowSubject.listen(Unit)

    private suspend fun doGetLaboratoryResearches(): List<GetLaboratoryResearchResponseDataEntity> =
        wrapBackendExceptions(userRepository) {
            laboratoryResearchSource.getLaboratoryResearches()
        }

    override fun reloadGetLaboratoryResearches() {
        laboratoryResearchesLazyFlowSubject.reloadAll()
    }


    override fun createLaboratoryResearch(
        createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity
    ): Flow<ResultState<CreateLaboratoryResearchResponseDataEntity>> =
        laboratoryResearchCreateLazyFlowSubject.listen(createLaboratoryResearchRequestDataEntity)


    private suspend fun doCreateLaboratoryResearches(createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity): CreateLaboratoryResearchResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            laboratoryResearchSource.createLaboratoryResearch(createLaboratoryResearchRequestDataEntity)
        }

    override fun reloadCreateLaboratoryResearch(createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity) {
        laboratoryResearchCreateLazyFlowSubject.reloadArgument(createLaboratoryResearchRequestDataEntity)
    }


    override fun updateLaboratoryResearch(
        updateLaboratoryResearchIdDataEntity: UpdateLaboratoryResearchIdDataEntity
    ): Flow<ResultState<UpdateLaboratoryResearchResponseDataEntity>> =
        laboratoryResearchUpdateLazyFlowSubject.listen(updateLaboratoryResearchIdDataEntity)

    private suspend fun doUpdateLaboratoryResearch(updateLaboratoryResearchIdDataEntity: UpdateLaboratoryResearchIdDataEntity)
            : UpdateLaboratoryResearchResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            laboratoryResearchSource.updateLaboratoryResearch(
                laboratoryResearchId = updateLaboratoryResearchIdDataEntity.laboratoryResearchId,
                updateLaboratoryResearchRequestDataEntity = UpdateLaboratoryResearchRequestDataEntity(
                    highDensityCholesterol = updateLaboratoryResearchIdDataEntity.highDensityCholesterol,
                    lowDensityCholesterol = updateLaboratoryResearchIdDataEntity.lowDensityCholesterol,
                    triglycerides = updateLaboratoryResearchIdDataEntity.triglycerides,
                    lipoprotein = updateLaboratoryResearchIdDataEntity.lipoprotein,
                    highlySensitiveCReactiveProtein = updateLaboratoryResearchIdDataEntity.highlySensitiveCReactiveProtein,
                    atherogenicityCoefficient = updateLaboratoryResearchIdDataEntity.atherogenicityCoefficient,
                    creatinine = updateLaboratoryResearchIdDataEntity.creatinine,
                    atheroscleroticPlaquesPresence = updateLaboratoryResearchIdDataEntity.atheroscleroticPlaquesPresence,
                )
            )
        }

    override fun reloadUpdateLaboratoryResearch(
        updateLaboratoryResearchIdDataEntity: UpdateLaboratoryResearchIdDataEntity
    ) {
        laboratoryResearchUpdateLazyFlowSubject.reloadArgument(updateLaboratoryResearchIdDataEntity)
    }
}