package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.lifestyle.tests.StenocardiaSymptomsTestRepository
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay

class StenocardiaSymptomsTestViewModel(
    private val screen: StenocardiaSymptomsTestFragment.Screen,
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val stenocardiaSymptomsTestRepository = Singletons.stenocardiaSymptomsTestRepository

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

    fun finish(result: Int) = viewModelScope.safeLaunch {
        screen.lifestyleTestListener.returnStenocardiaSymptomsResult(result) {
            navigator.goBack()
        }
    }
}