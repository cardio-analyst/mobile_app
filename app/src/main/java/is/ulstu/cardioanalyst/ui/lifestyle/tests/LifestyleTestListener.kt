package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

interface LifestyleTestListener {

    fun returnStenocardiaSymptomsResult(score: Int, action: () -> Unit)

    fun returnTreatmentAdherenceResult(results: Triple<Double, Double, Double>, action: () -> Unit)
}