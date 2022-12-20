package `is`.ulstu.cardioanalyst.models.basic_indicators

import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.BasicIndicatorsSource
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BasicIndicatorsDBRepository @Inject constructor(
    private val basicIndicatorsSource: BasicIndicatorsSource,
    private val userRepository: IUserRepository,
) : IBasicIndicatorsRepository {

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


    override fun getBasicIndicators(): Flow<Result<List<GetBasicIndicatorResponseEntity>>> =
        basicIndicatorsLazyFlowSubject.listen(Unit)

    private suspend fun doGetBasicIndicators(): List<GetBasicIndicatorResponseEntity> =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getBasicIndicators()
        }

    override fun reloadBasicIndicators() {
        basicIndicatorsLazyFlowSubject.reloadAll()
    }


    override fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity)
            : Flow<Result<CreateBasicIndicatorResponseEntity>> =
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
            : Flow<Result<UpdateBasicIndicatorResponseEntity>> =
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


    override fun getCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): Flow<Result<GetCVERiskResponseEntity>> =
        cveRiskLazyFlowSubject.listen(getCVERiskRequestEntity)


    private suspend fun doGetCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetCVERiskResponseEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getCVERisk(getCVERiskRequestEntity)
        }


    override fun getIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): Flow<Result<GetIdealAgeResponseEntity>> =
        idealAgeLazyFlowSubject.listen(getCVERiskRequestEntity)

    private suspend fun doGetIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetIdealAgeResponseEntity =
        wrapBackendExceptions(userRepository) {
            basicIndicatorsSource.getIdealAge(getCVERiskRequestEntity)
        }

}