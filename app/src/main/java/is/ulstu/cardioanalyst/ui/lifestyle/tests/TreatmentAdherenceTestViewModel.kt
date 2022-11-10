package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.TreatmentAdherenceTestRepository
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope
import java.util.*

class TreatmentAdherenceTestViewModel(
    private val screen: TreatmentAdherenceTestFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val treatmentAdherenceTestRepository = Singletons.treatmentAdherenceTestRepository

    /**
     * @see TreatmentAdherenceTestRepository
     */
    private val questions: Queue<TreatmentAdherenceTestRepository.Question> =
        LinkedList(treatmentAdherenceTestRepository.questions)

    fun getNextQuestion(): TreatmentAdherenceTestRepository.Question? = questions.poll()

    fun finish(results: Triple<Double, Double, Double>) = viewModelScope.safeLaunch {
        screen.lifestyleTestListener.returnTreatmentAdherenceResult(results) {
            navigator.goBack()
        }
    }
}