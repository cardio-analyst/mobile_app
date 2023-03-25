package com.example.data.repositories.recommendations.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitRecommendationsSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), RecommendationsSource {

    private val recommendationsApi = retrofit.create(RecommendationsApi::class.java)

    override suspend fun getRecommendations(): List<GetRecommendationsResponseEntity> =
        wrapRetrofitExceptions { recommendationsApi.getRecommendations().recommendations }

    override suspend fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): SendReportResponseEntity =
        wrapRetrofitExceptions { recommendationsApi.sendReportToEmail(sendReportRequestEntity) }

}