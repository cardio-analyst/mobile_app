package `is`.ulstu.cardioanalyst.models.recommendations.sources

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsArrayResponseEntity
import retrofit2.http.GET

interface RecommendationsApi {

    @GET("recommendations")
    suspend fun getRecommendations(): GetRecommendationsArrayResponseEntity

}