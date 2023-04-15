package com.example.data.repositories.recommendations.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitRecommendationsSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), RecommendationsSource {

    private val recommendationsApi = retrofit.create(RecommendationsApi::class.java)

    override suspend fun getRecommendations(): List<GetRecommendationsResponseDataEntity> =
        wrapRetrofitExceptions { recommendationsApi.getRecommendations().recommendations }

    override suspend fun sendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity): SendReportResponseDataEntity =
        wrapRetrofitExceptions { recommendationsApi.sendReportToEmail(sendReportRequestDataEntity) }

}