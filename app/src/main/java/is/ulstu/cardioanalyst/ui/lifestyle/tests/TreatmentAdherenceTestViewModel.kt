package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import `is`.ulstu.cardioanalyst.models.lifestyle.tests.TreatmentAdherenceTestRepository
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TreatmentAdherenceTestViewModel @Inject constructor(
    private val treatmentAdherenceTestRepository: TreatmentAdherenceTestRepository,
) : BaseViewModel() {

    /**
     * @see TreatmentAdherenceTestRepository
     */
    private val questions: Queue<TreatmentAdherenceTestRepository.Question> =
        LinkedList(treatmentAdherenceTestRepository.questions)

    fun getNextQuestion(): TreatmentAdherenceTestRepository.Question? = questions.poll()

    fun finish(results: Triple<Double, Double, Double>) {
        treatmentAdherenceTestRepository.results = results
    }
}