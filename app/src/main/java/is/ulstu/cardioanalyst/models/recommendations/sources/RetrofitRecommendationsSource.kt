package `is`.ulstu.cardioanalyst.models.recommendations.sources

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig

class RetrofitRecommendationsSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), RecommendationsSource {

    private val recommendationsApi = retrofit.create(RecommendationsApi::class.java)

    override suspend fun getRecommendations(): List<GetRecommendationsResponseEntity> =
        wrapRetrofitExceptions { recommendationsApi.getRecommendations().recommendations }

}