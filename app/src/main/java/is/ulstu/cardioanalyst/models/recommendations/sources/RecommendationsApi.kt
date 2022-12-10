package `is`.ulstu.cardioanalyst.models.recommendations.sources

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsArrayResponseEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportRequestEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportResponseEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface RecommendationsApi {

    @GET("recommendations")
    suspend fun getRecommendations(): GetRecommendationsArrayResponseEntity

    @POST("recommendations/send")
    suspend fun sendReportToEmail(
        @Body sendReportRequestEntity: SendReportRequestEntity
    ): SendReportResponseEntity

}