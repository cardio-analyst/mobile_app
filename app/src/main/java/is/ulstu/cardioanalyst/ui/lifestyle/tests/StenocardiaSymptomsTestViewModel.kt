package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.StenocardiaSymptomsTestRepository
import com.example.presentation.BaseViewModel
import javax.inject.Inject

@HiltViewModel
class StenocardiaSymptomsTestViewModel @Inject constructor(
    private val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository,
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
    }

}