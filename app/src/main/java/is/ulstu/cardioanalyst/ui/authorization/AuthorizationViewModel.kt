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
    val uiActions: UiActions,
    private val userRepository: IUserRepository,
) : BaseViewModel(uiActions) {

    private val _userSignIn = SingleLiveEvent<Result<Unit>>()
    val userSignIn = _userSignIn.share()

    fun onEnter(loginOrEmail: String, password: String) = viewModelScope.safeLaunch {
        userRepository.signInUser(loginOrEmail, password).collect {
            _userSignIn.value = it
        }
    }

    fun reload(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)
}