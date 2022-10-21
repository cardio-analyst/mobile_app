package `is`.ulstu.cardioanalyst.ui.authorization

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.cardioanalyst.ui.registration.RegistrationFragment
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class AuthorizationViewModel(
    private val navigator: Navigator,
    val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val userRepository: IUserRepository = Singletons.userRepository

    private val _userSignIn = MutableLiveData<Result<Unit>>()
    val userSignIn = _userSignIn.share()

    private fun launchApp() =
        navigator.addFragmentToScreen(R.id.fragmentContainer, NavigationFragment.Screen())

    fun onEnter(loginOrEmail: String, password: String) = viewModelScope.safeLaunch {
        userRepository.signInUser(loginOrEmail, password).collect {
            _userSignIn.value = it
        }
    }

    fun reload(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)

    fun onSuccessSignIn() {
        if (navigator.getBackstackFragmentCount() > 0) {
            navigator.goBack()
        } else {
            launchApp()
        }
    }

    fun checkCurrentAuthToken() = viewModelScope.safeLaunch {
        userRepository.refreshUserAccessToken()
        if (Singletons.appSettings.getUserAccountAccessToken() != null)
            launchApp()
    }

    fun onRegister() {
        navigator.launch(RegistrationFragment.Screen())
    }
}