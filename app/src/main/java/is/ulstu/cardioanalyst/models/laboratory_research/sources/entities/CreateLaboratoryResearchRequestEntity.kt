package `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities

data class CreateLaboratoryResearchRequestEntity(
    val highDensityCholesterol: Double,
    val lowDensityCholesterol: Double,
    val triglycerides: Double,
    val lipoprotein: Double,
    val highlySensitiveCReactiveProtein: Double,
    val atherogenicityCoefficient: Double,
    val creatinine: Double,
    val atheroscleroticPlaquesPresence: Boolean,
)
