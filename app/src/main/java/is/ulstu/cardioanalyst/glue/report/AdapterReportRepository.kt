package `is`.ulstu.cardioanalyst.glue.report

import com.example.common.flows.ResultState
import com.example.data.repositories.recommendations.IRecommendationsDataDataRepository
import com.example.report.domain.ReportRepository
import com.example.report.domain.entities.SendReportRequestEntity
import com.example.report.domain.entities.SendReportResponseEntity
import `is`.ulstu.cardioanalyst.glue.report.mappers.toSendReportRequestDataEntity
import `is`.ulstu.cardioanalyst.glue.report.mappers.toSendReportResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterReportRepository @Inject constructor(
    private val reportRepository: IRecommendationsDataDataRepository,
) : ReportRepository {
    override fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity)
            : Flow<ResultState<SendReportResponseEntity>> =
        reportRepository.sendReportToEmail(sendReportRequestEntity.toSendReportRequestDataEntity())
            .map { resultState ->
                resultState.map { sendReportResponseDataEntity ->
                    sendReportResponseDataEntity.toSendReportResponseEntity()
                }
            }

    override fun reloadSendReportToEmail(sendReportRequestEntity: SendReportRequestEntity) =
        reportRepository.reloadSendReportToEmail(sendReportRequestEntity.toSendReportRequestDataEntity())
}