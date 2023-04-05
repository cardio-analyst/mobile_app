package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import com.example.data.repositories.lifestyle.tests.TreatmentAdherenceTestRepository
import com.example.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TreatmentAdherenceTestViewModel @Inject constructor(
    private val treatmentAdherenceTestRepository: TreatmentAdherenceTestRepository,
    private val tabsController: TabsController,
) : BaseViewModel() {

    /**
     * @see TreatmentAdherenceTestRepository
     */
    private val questions: Queue<TreatmentAdherenceTestRepository.Question> =
        LinkedList(treatmentAdherenceTestRepository.questions)

    fun getNextQuestion(): TreatmentAdherenceTestRepository.Question? = questions.poll()

    fun finish(results: Triple<Double, Double, Double>) {
        treatmentAdherenceTestRepository.results = results
        tabsController.goBack()
    }
}