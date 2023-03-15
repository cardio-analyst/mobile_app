package `is`.ulstu.cardioanalyst.ui.authorization

import androidx.lifecycle.viewModelScope
import com.example.common.flows.ResultState
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import com.example.presentation.SingleLiveEvent
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    val uiActions: UiAction,
    private val userRepository: IUserRepository,
) : BaseViewModel(uiActions) {

    private val _userSignIn = SingleLiveEvent<ResultState<Unit>>()
    val userSignIn = _userSignIn.share()

    fun onEnter(loginOrEmail: String, password: String) = viewModelScope.safeLaunch {
        userRepository.signInUser(loginOrEmail, password).collect {
            _userSignIn.value = it
        }
    }

    fun reload(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)
}