package `is`.ulstu.cardioanalyst.presentation.ui.lifestyle.tests

import com.example.data.repositories.lifestyle.tests.StenocardiaSymptomsTestRepository
import com.example.presentation.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject

@HiltViewModel
class StenocardiaSymptomsTestViewModel @Inject constructor(
    private val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository,
    private val tabsController: TabsController,
) : BaseViewModel() {

    /**
     * @see StenocardiaSymptomsTestRepository
     */
    private val questions: List<StenocardiaSymptomsTestRepository.Question> =
        stenocardiaSymptomsTestRepository.questions

    private var currentQuestion = -1

    fun getNextQuestion(): StenocardiaSymptomsTestRepository.Question? {
        currentQuestion++
        return if (currentQuestion < questions.size)
            questions[currentQuestion]
        else {
            null
        }
    }

    fun finish(result: Int) {
        stenocardiaSymptomsTestRepository.scoreResult = result
        tabsController.goBack()
    }

}