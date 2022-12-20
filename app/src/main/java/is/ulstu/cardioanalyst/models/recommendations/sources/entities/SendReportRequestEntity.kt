package `is`.ulstu.cardioanalyst.models.recommendations.sources.entities

data class SendReportRequestEntity(
    val receiver: String,
    val sendMyself: Boolean,
)
