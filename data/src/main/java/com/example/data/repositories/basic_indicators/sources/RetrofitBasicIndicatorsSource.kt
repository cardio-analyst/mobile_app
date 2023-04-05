package com.example.data.repositories.basic_indicators.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.basic_indicators.sources.entities.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitBasicIndicatorsSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), BasicIndicatorsSource {

    private val basicIndicatorsApi = retrofit.create(BasicIndicatorsApi::class.java)

    override suspend fun getBasicIndicators(): List<GetBasicIndicatorResponseDataEntity> =
        wrapRetrofitExceptions { basicIndicatorsApi.getBasicIndicators().basicIndicators }

    override suspend fun createBasicIndicator(createBasicIndicatorRequestDataEntity: CreateBasicIndicatorRequestDataEntity): CreateBasicIndicatorResponseDataEntity =
        wrapRetrofitExceptions {
            basicIndicatorsApi.createBasicIndicator(
                createBasicIndicatorRequestDataEntity
            )
        }

    override suspend fun updateBasicIndicator(
        basicIndicatorId: Long,
        updateBasicIndicatorRequestDataEntity: UpdateBasicIndicatorRequestDataEntity
    ): UpdateBasicIndicatorResponseDataEntity =
        wrapRetrofitExceptions {
            basicIndicatorsApi.updateBasicIndicator(
                basicIndicatorId,
                updateBasicIndicatorRequestDataEntity
            )
        }

    override suspend fun getCVERisk(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): GetCVERiskResponseDataEntity =
        wrapRetrofitExceptions {
            basicIndicatorsApi.getCVERisk(
                getCVERiskRequestDataEntity.gender,
                getCVERiskRequestDataEntity.smoking,
                getCVERiskRequestDataEntity.sbpLevel,
                getCVERiskRequestDataEntity.totalCholesterolLevel
            )
        }

    override suspend fun getIdealAge(getCVERiskRequestDataEntity: GetCVERiskRequestDataEntity): GetIdealAgeResponseDataEntity =
        wrapRetrofitExceptions {
            basicIndicatorsApi.getIdealAge(
                getCVERiskRequestDataEntity.gender,
                getCVERiskRequestDataEntity.smoking,
                getCVERiskRequestDataEntity.sbpLevel,
                getCVERiskRequestDataEntity.totalCholesterolLevel
            )
        }
}