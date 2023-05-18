package `is`.ulstu.cardioanalyst.glue.basic_indicators

import com.example.basic_indicators.domain.BasicIndicatorsRepository
import com.example.basic_indicators.domain.entities.*
import com.example.common.flows.ResultState
import com.example.data.repositories.basic_indicators.IBasicIndicatorsDataDataRepository
import `is`.ulstu.cardioanalyst.glue.basic_indicators.mappers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterBasicIndicatorsRepository @Inject constructor(
    private val basicIndicatorsRepository: IBasicIndicatorsDataDataRepository,
) : BasicIndicatorsRepository {

    override fun getBasicIndicators(): Flow<ResultState<List<GetBasicIndicatorResponseEntity>>> =
        basicIndicatorsRepository.getBasicIndicators().map { resultState ->
            resultState.map { getBasicIndicatorResponseDataEntities ->
                getBasicIndicatorResponseDataEntities.map { getBasicIndicatorResponseDataEntity ->
                    getBasicIndicatorResponseDataEntity.toGetBasicIndicatorResponseEntity()
                }
            }
        }

    override fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity): Flow<ResultState<CreateBasicIndicatorResponseEntity>> =
        basicIndicatorsRepository.createBasicIndicator(createBasicIndicatorRequestEntity.toCreateBasicIndicatorRequestDataEntity())
            .map { resultState ->
                resultState.map { createBasicIndicatorResponseDataEntity ->
                    createBasicIndicatorResponseDataEntity.toCreateBasicIndicatorResponseEntity()
                }
            }

    override fun updateBasicIndicator(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity): Flow<ResultState<UpdateBasicIndicatorResponseEntity>> =
        basicIndicatorsRepository.updateBasicIndicator(updateBasicIndicatorIdEntity.toUpdateBasicIndicatorIdDataEntity())
            .map { resultState ->
                resultState.map { updateBasicIndicatorResponseDataEntity ->
                    updateBasicIndicatorResponseDataEntity.toUpdateBasicIndicatorResponseEntity()
                }
            }

    override fun reloadBasicIndicators() =
        basicIndicatorsRepository.reloadBasicIndicators()

    override fun reloadCreateBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity) =
        basicIndicatorsRepository.reloadCreateBasicIndicator(
            createBasicIndicatorRequestEntity.toCreateBasicIndicatorRequestDataEntity()
        )

    override fun reloadUpdateBasicIndicator(updateBasicIndicatorIdEntity: UpdateBasicIndicatorIdEntity) =
        basicIndicatorsRepository.reloadUpdateBasicIndicator(
            updateBasicIndicatorIdEntity.toUpdateBasicIndicatorIdDataEntity()
        )

    override fun getCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): Flow<ResultState<GetCVERiskResponseEntity>> =
        basicIndicatorsRepository.getCVERisk(getCVERiskRequestEntity.toGetCVERiskRequestDataEntity())
            .map { resultState ->
                resultState.map { getCVERiskResponseDataEntity ->
                    getCVERiskResponseDataEntity.toGetCVERiskResponseEntity()
                }
            }

    override fun getIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): Flow<ResultState<GetIdealAgeResponseEntity>> =
        basicIndicatorsRepository.getIdealAge(getCVERiskRequestEntity.toGetCVERiskRequestDataEntity())
            .map { resultState ->
                resultState.map { getIdealAgeResponseDataEntity ->
                    getIdealAgeResponseDataEntity.toGetIdealAgeResponseEntity()
                }
            }
}