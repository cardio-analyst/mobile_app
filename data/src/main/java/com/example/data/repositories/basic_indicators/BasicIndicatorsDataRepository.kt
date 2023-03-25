package com.example.data.repositories.basic_indicators

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.basic_indicators.sources.BasicIndicatorsSource
import com.example.data.repositories.basic_indicators.sources.entities.*
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasicIndicatorsDataRepository @Inject constructor(
    private val basicIndicatorsSource: BasicIndicatorsSource,
    private val userRepository: IUserDataRepository,
) : IBasicIndicatorsDataRepository {

    // --- Lazy Repository Flows for observers

    private val basicIndicatorsLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetBasicIndicatorResponseEntity>> {
            doGetBasicIndicators()
        }

    private val basicIndicatorCreateLazyFlowSubject =
        LazyFlowSubject<CreateBasicIndicatorRequestEntity, CreateBasicIndicatorResponseEntity>
        { createBasicIndicatorRequestEntity ->
            doCreateBasicIndicators(createBasicIndicatorRequestEntity)
        }

    private val basicIndicatorUpdateLazyFlowSubject =
        LazyFlowSubject<UpdateBasicIndicatorIdEntity, UpdateBasicIndicatorResponseEntity>
        { updateBasicIndicatorIdEntity ->
            doUpdateBasicIndicators(updateBasicIndicatorIdEntity)
        }

    private val cveRiskLazyFlowSubject =
        LazyFlowSubject<GetCVERiskRequestEntity, GetCVERiskResponseEntity> { getCVERiskRequestEntity ->
            doGetCVERisk(getCVERiskRequestEntity)
        }

    private val idealAgeLazyFlowSubject =
        LazyFlowSubject<GetCVERiskRequestEntity, GetIdealAgeResponseEntity> { getCVERiskRequestEntity ->
            doGetIdealAge(getCVERiskRequestEntity)
        }


    override fun getBasicIndicators(): Flow<ResultState<List<GetBasicIndicatorResponseEntity>>> =
        basicIndicatorsLazyFlowSubject.listen(Unit)

    private suspend fun doGetBasicIndicators(): List<GetBasicIndicatorResponseEntity> =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getBasicIndicators()
        }

    override fun reloadBasicIndicators() {
        basicIndicatorsLazyFlowSubject.reloadAll()
    }


    override fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)
            : Flow<ResultState<CreateBasicIndicatorResponseEntity>> =
        basicIndicatorCreateLazyFlowSubject.listen(createBasicIndicatorRequestEntity)

    private suspend fun doCreateBasicIndicators(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)
            : CreateBasicIndicatorResponseEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.createBasicIndicator(createBasicIndicatorRequestEntity)
        }

    override fun reloadCreateBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity) {
        basicIndicatorCreateLazyFlowSubject.reloadArgument(createBasicIndicatorRequestEntity)
    }


    override fun updateBasicIndicator(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity)
            : Flow<ResultState<UpdateBasicIndicatorResponseEntity>> =
        basicIndicatorUpdateLazyFlowSubject.listen(updateBasicIndicatorIdEntity)

    private suspend fun doUpdateBasicIndicators(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity)
            : UpdateBasicIndicatorResponseEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.updateBasicIndicator(
                basicIndicatorId = updateBasicIndicatorIdEntity.basicIndicatorId,
                updateBasicIndicatorRequestEntity = UpdateBasicIndicatorRequestEntity(
                    weight = updateBasicIndicatorIdEntity.weight,
                    height = updateBasicIndicatorIdEntity.height,
                    bodyMassIndex = updateBasicIndicatorIdEntity.bodyMassIndex,
                    waistSize = updateBasicIndicatorIdEntity.waistSize,
                    gender = updateBasicIndicatorIdEntity.gender,
                    sbpLevel = updateBasicIndicatorIdEntity.sbpLevel,
                    smoking = updateBasicIndicatorIdEntity.smoking,
                    totalCholesterolLevel = updateBasicIndicatorIdEntity.totalCholesterolLevel,
                    cvEventsRiskValue = updateBasicIndicatorIdEntity.cvEventsRiskValue,
                    idealCardiovascularAgesRange = updateBasicIndicatorIdEntity.idealCardiovascularAgesRange,
                )
            )
        }

    override fun reloadUpdateBasicIndicator(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity) {
        basicIndicatorUpdateLazyFlowSubject.reloadArgument(updateBasicIndicatorIdEntity)
    }


    override fun getCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): Flow<ResultState<GetCVERiskResponseEntity>> =
        cveRiskLazyFlowSubject.listen(getCVERiskRequestEntity)


    private suspend fun doGetCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetCVERiskResponseEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getCVERisk(getCVERiskRequestEntity)
        }


    override fun getIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): Flow<ResultState<GetIdealAgeResponseEntity>> =
        idealAgeLazyFlowSubject.listen(getCVERiskRequestEntity)

    private suspend fun doGetIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetIdealAgeResponseEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getIdealAge(getCVERiskRequestEntity)
        }

}