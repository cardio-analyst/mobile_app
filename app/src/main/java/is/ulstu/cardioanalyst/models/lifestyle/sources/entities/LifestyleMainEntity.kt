package `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities

data class LifestyleMainEntity(
    var familyStatus: String,
    var eventsParticipation: String,
    var physicalActivity: String,
    var workStatus: String,
    var significantValueHigh: String,
    var significantValueMedium: String,
    var significantValueLow: String,
    var anginaScore: Int,
    var adherenceDrugTherapy: Double,
    var adherenceMedicalSupport: Double,
    var adherenceLifestyleMod: Double,
)
