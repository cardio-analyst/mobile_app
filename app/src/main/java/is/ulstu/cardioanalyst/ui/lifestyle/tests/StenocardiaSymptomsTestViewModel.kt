package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import `is`.ulstu.cardioanalyst.models.lifestyle.tests.StenocardiaSymptomsTestRepository
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class StenocardiaSymptomsTestViewModel @Inject constructor(
    private val navigator: Navigator,
    userSettings: UserSettings,
    private val stenocardiaSymptomsTestRepository: StenocardiaSymptomsTestRepository,
) : BaseViewModel(navigator, userSettings) {

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
        navigator.goBack()
    }

}