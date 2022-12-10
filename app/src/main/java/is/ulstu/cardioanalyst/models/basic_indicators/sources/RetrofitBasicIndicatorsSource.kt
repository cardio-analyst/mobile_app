package `is`.ulstu.cardioanalyst.models.basic_indicators.sources

import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.*
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBasicIndicatorsSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), BasicIndicatorsSource {

    private val basicIndicatorsApi = retrofit.create(BasicIndicatorsApi::class.java)

    override suspend fun getBasicIndicators(): List<GetBasicIndicatorResponseEntity> =
        wrapRetrofitExceptions { basicIndicatorsApi.getBasicIndicators().basicIndicators }

    override suspend fun createBasicIndicator(createBasicIndicatorRequestEntity: CreateBasicIndicatorRequestEntity): CreateBasicIndicatorResponseEntity =
        wrapRetrofitExceptions {
            basicIndicatorsApi.createBasicIndicator(
                createBasicIndicatorRequestEntity
            )
        }

    override suspend fun updateBasicIndicator(
        basicIndicatorId: Long,
        updateBasicIndicatorRequestEntity: UpdateBasicIndicatorRequestEntity
    ): UpdateBasicIndicatorResponseEntity =
        wrapRetrofitExceptions {
            basicIndicatorsApi.updateBasicIndicator(
                basicIndicatorId,
                updateBasicIndicatorRequestEntity
            )
        }

    override suspend fun getCVERisk(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetCVERiskResponseEntity =
        wrapRetrofitExceptions { basicIndicatorsApi.getCVERisk(
            getCVERiskRequestEntity.gender,
            getCVERiskRequestEntity.smoking,
            getCVERiskRequestEntity.sbpLevel,
            getCVERiskRequestEntity.totalCholesterolLevel
        ) }

    override suspend fun getIdealAge(getCVERiskRequestEntity: GetCVERiskRequestEntity): GetIdealAgeResponseEntity =
        wrapRetrofitExceptions { basicIndicatorsApi.getIdealAge(
            getCVERiskRequestEntity.gender,
            getCVERiskRequestEntity.smoking,
            getCVERiskRequestEntity.sbpLevel,
            getCVERiskRequestEntity.totalCholesterolLevel
        ) }
}