package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.TreatmentAdherenceTestRepository
import com.example.presentation.BaseViewModel
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