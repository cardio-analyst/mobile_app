package `is`.ulstu.cardioanalyst.models.recommendations

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportRequestEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportResponseEntity
import `is`.ulstu.foundation.model.Repository
import com.example.common.flows.ResultState
import kotlinx.coroutines.flow.Flow

interface IRecommendationsRepository : Repository {

    /**
     * Get recommendations
     * @return [Flow]
     */
    fun getRecommendations(): Flow<ResultState<List<GetRecommendationsResponseEntity>>>

    /**
     * Reload getting recommendations
     */
    fun reloadGetRecommendations()

    /**
     * Send report to email
     */
    fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): Flow<ResultState<SendReportResponseEntity>>

    /**
     * Reload sending report
     */
    fun reloadSendReportToEmail(sendReportRequestEntity: SendReportRequestEntity)

}