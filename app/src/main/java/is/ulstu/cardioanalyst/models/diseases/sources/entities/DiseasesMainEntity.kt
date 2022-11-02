package `is`.ulstu.cardioanalyst.models.diseases.sources.entities

data class DiseasesMainEntity(
    val cvdPredisposed: Boolean,
    val takesStatins: Boolean,
    val hasChronicKidneyDisease: Boolean,
    val hasArterialHypertension: Boolean,
    val hasIschemicHeartDisease: Boolean,
    val hasTypeTwoDiabetes: Boolean,
    val hadInfarctionOrStroke: Boolean,
    val hasAtherosclerosis: Boolean,
    val hasOtherCVD: Boolean
)