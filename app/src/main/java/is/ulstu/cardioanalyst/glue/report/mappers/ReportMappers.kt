package `is`.ulstu.cardioanalyst.glue.report.mappers

import com.example.data.repositories.recommendations.sources.entities.SendReportRequestDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseDataEntity
import com.example.report.domain.entities.SendReportRequestEntity
import com.example.report.domain.entities.SendReportResponseEntity

fun SendReportResponseDataEntity.toSendReportResponseEntity() = SendReportResponseEntity(
    result = result,
)

fun SendReportRequestEntity.toSendReportRequestDataEntity() = SendReportRequestDataEntity(
    receiver = receiver,
    sendMyself = sendMyself,
)