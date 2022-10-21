package `is`.ulstu.cardioanalyst.ui.authorization

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.cardioanalyst.ui.registration.RegistrationFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope

class AuthorizationViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(uiActions) {

    private val userRepository: IUserRepository = Singletons.userRepository
    private val appSettings = Singletons.appSettings

    private fun launchApp() =
        navigator.addFragmentToScreen(R.id.fragmentContainer, NavigationFragment.Screen())

    fun onEnter(loginOrEmail: String, password: String) = viewModelScope.safeLaunch {
        userRepository.singInUser(loginOrEmail, password)
        launchApp()
    }

    fun checkCurrentAuthToken() = if (appSettings.getCurrentToken() != null) {
        launchApp()
        true
    } else false

    fun onRegister() {
        navigator.launch(RegistrationFragment.Screen())
    }
}