package `is`.ulstu.cardioanalyst.ui.authorization

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.cardioanalyst.ui.registration.RegistrationFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel

class AuthorizationViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
    private val userRepository: IUserRepository,
) : BaseViewModel() {

    fun onEnter(login: String, password: String) {
        try {
            userRepository.enterUser(login, password)
            navigator.addFragmentToScreen(R.id.fragmentContainer, NavigationFragment.Screen())
        } catch (e : Exception) {
            uiActions.toast(e.message ?: "Something wrong")
        }
    }

    fun onRegister() {
        navigator.launch(RegistrationFragment.Screen())
    }
}