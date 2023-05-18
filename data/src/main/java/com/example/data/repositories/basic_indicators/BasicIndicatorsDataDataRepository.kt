package com.example.data.repositories.basic_indicators

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.basic_indicators.sources.BasicIndicatorsSource
import com.example.data.repositories.basic_indicators.sources.entities.*
import com.example.data.repositories.users.IUserDataDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasicIndicatorsDataDataRepository @Inject constructor(
    private val basicIndicatorsSource: BasicIndicatorsSource,
    private val userRepository: IUserDataDataRepository,
) : IBasicIndicatorsDataDataRepository {

    // --- Lazy Repository Flows for observers

    private val basicIndicatorsLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetBasicIndicatorResponseDataEntity>> {
            doGetBasicIndicators()
        }

    private val basicIndicatorCreateLazyFlowSubject =
        LazyFlowSubject<CreateBasicIndicatorRequestDataEntity, CreateBasicIndicatorResponseDataEntity>
        { createBasicIndicatorRequestEntity ->
            doCreateBasicIndicators(createBasicIndicatorRequestEntity)
        }

    private val basicIndicatorUpdateLazyFlowSubject =
        LazyFlowSubject<UpdateBasicIndicatorIdDataEntity, UpdateBasicIndicatorResponseDataEntity>
        { updateBasicIndicatorIdEntity ->
            doUpdateBasicIndicators(updateBasicIndicatorIdEntity)
        }

    private val cveRiskLazyFlowSubject =
        LazyFlowSubject<GetCVERiskRequestDataEntity, GetCVERiskResponseDataEntity> { getCVERiskRequestEntity ->
            doGetCVERisk(getCVERiskRequestEntity)
        }

    private val idealAgeLazyFlowSubject =
        LazyFlowSubject<GetCVERiskRequestDataEntity, GetIdealAgeResponseDataEntity> { getCVERiskRequestEntity ->
            doGetIdealAge(getCVERiskRequestEntity)
        }


    override fun getBasicIndicators(): Flow<ResultState<List<GetBasicIndicatorResponseDataEntity>>> =
        basicIndicatorsLazyFlowSubject.listen(Unit)

    private suspend fun doGetBasicIndicators(): List<GetBasicIndicatorResponseDataEntity> =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getBasicIndicators()
        }

    override fun reloadBasicIndicators() {
        basicIndicatorsLazyFlowSubject.reloadAll()
    }


    override fun createBasicIndicator(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity)
            : Flow<ResultState<CreateBasicIndicatorResponseDataEntity>> =
        basicIndicatorCreateLazyFlowSubject.listen(createBasicIndicatorRequestDataEntity)

    private suspend fun doCreateBasicIndicators(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity)
            : CreateBasicIndicatorResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.createBasicIndicator(createBasicIndicatorRequestDataEntity)
        }

    override fun reloadCreateBasicIndicator(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity) {
        basicIndicatorCreateLazyFlowSubject.reloadArgument(createBasicIndicatorRequestDataEntity)
    }


    override fun updateBasicIndicator(updateBasicIndicatorIdDataEntity: UpdateBasicIndicatorIdDataEntity)
            : Flow<ResultState<UpdateBasicIndicatorResponseDataEntity>> =
        basicIndicatorUpdateLazyFlowSubject.listen(updateBasicIndicatorIdDataEntity)

    private suspend fun doUpdateBasicIndicators(updateBasicIndicatorIdDataEntity: UpdateBasicIndicatorIdDataEntity)
            : UpdateBasicIndicatorResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.updateBasicIndicator(
                basicIndicatorId = updateBasicIndicatorIdDataEntity.basicIndicatorId,
                updateBasicIndicatorRequestDataEntity = UpdateBasicIndicatorRequestDataEntity(
                    weight = updateBasicIndicatorIdDataEntity.weight,
                    height = updateBasicIndicatorIdDataEntity.height,
                    bodyMassIndex = updateBasicIndicatorIdDataEntity.bodyMassIndex,
                    waistSize = updateBasicIndicatorIdDataEntity.waistSize,
                    gender = updateBasicIndicatorIdDataEntity.gender,
                    sbpLevel = updateBasicIndicatorIdDataEntity.sbpLevel,
                    smoking = updateBasicIndicatorIdDataEntity.smoking,
                    totalCholesterolLevel = updateBasicIndicatorIdDataEntity.totalCholesterolLevel,
                    cvEventsRiskValue = updateBasicIndicatorIdDataEntity.cvEventsRiskValue,
                    idealCardiovascularAgesRange = updateBasicIndicatorIdDataEntity.idealCardiovascularAgesRange,
                )
            )
        }

    override fun reloadUpdateBasicIndicator(updateBasicIndicatorIdDataEntity: UpdateBasicIndicatorIdDataEntity) {
        basicIndicatorUpdateLazyFlowSubject.reloadArgument(updateBasicIndicatorIdDataEntity)
    }


    override fun getCVERisk(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): Flow<ResultState<GetCVERiskResponseDataEntity>> =
        cveRiskLazyFlowSubject.listen(getCVERiskRequestDataEntity)


    private suspend fun doGetCVERisk(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): GetCVERiskResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getCVERisk(getCVERiskRequestDataEntity)
        }


    override fun getIdealAge(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): Flow<ResultState<GetIdealAgeResponseDataEntity>> =
        idealAgeLazyFlowSubject.listen(getCVERiskRequestDataEntity)

    private suspend fun doGetIdealAge(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): GetIdealAgeResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getIdealAge(getCVERiskRequestDataEntity)
        }

}