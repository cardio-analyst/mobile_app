package `is`.ulstu.cardioanalyst.models.recommendations.sources

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportRequestEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportResponseEntity
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig
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