package `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities

data class UpdateBasicIndicatorRequestEntity(
    val weight: Double,
    val height: Double,
    val bodyMassIndex: Double,
    val waistSize: Double,
    val gender: String,
    val sbpLevel: Double,
    val smoking: Boolean,
    val totalCholesterolLevel: Double,
    val cvEventsRiskValue: Int,
    val idealCardiovascularAgesRange: String
)
