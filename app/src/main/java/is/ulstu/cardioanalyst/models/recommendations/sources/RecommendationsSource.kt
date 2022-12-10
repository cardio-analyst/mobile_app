package `is`.ulstu.cardioanalyst.models.recommendations.sources

import `is`.ulstu.cardioanalyst.app.BackendExceptions
import `is`.ulstu.cardioanalyst.app.ConnectionException
import `is`.ulstu.cardioanalyst.app.ParseBackendResponseException
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportRequestEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportResponseEntity

interface RecommendationsSource {

    /**
     * Get the user laboratoryResearches
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [List]
     */
    suspend fun getRecommendations(): List<GetRecommendationsResponseEntity>

    /**
     * Send user information to email (doctor/himSelf)
     * @throws ConnectionException
     * @throws BackendExceptions
     * @throws ParseBackendResponseException
     * @return [SendReportResponseEntity]
     */
    suspend fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): SendReportResponseEntity

}