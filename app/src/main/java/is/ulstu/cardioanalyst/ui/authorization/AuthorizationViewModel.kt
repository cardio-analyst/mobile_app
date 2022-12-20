package `is`.ulstu.cardioanalyst.ui.authorization

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.AutoSignInException
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.cardioanalyst.ui.registration.RegistrationFragment
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Pending
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.SingleLiveEvent
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    private val navigator: Navigator,
    private val userSettings: UserSettings,
    val uiActions: UiActions,
    private val userRepository: IUserRepository,
    private val navigationFragment: NavigationFragment,
) : BaseViewModel(navigator, userSettings, uiActions) {

    private val _userSignIn = SingleLiveEvent<Result<Unit>>()
    val userSignIn = _userSignIn.share()

    private val _autoSignIn = SingleLiveEvent<Result<Unit>>()
    val autoSignIn = _autoSignIn.share()


    private fun launchApp() =
        navigator.addFragmentToScreen(R.id.fragmentContainer, navigationFragment)

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
        _autoSignIn.value = Pending()
        if (userSettings.getCurrentRefreshToken() != null) {
            try {
                userRepository.refreshUserAccessToken()
            } catch (e: Exception) {
                val ex = AutoSignInException()
                val error = Error<Unit>(ex)
                _autoSignIn.value = error
                return@safeLaunch
            }

            if (userSettings.getUserAccountAccessToken() != null) {
                _autoSignIn.value = Success(value = Unit)
                launchApp()
                return@safeLaunch
            }
        }
        val ex = AutoSignInException()
        val error = Error<Unit>(ex)
        _autoSignIn.value = error
    }

    fun onRegister() {
        navigator.launch(RegistrationFragment())
    }
}