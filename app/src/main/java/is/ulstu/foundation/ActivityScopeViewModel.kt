package `is`.ulstu.foundation

import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.IntermediateNavigator
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.app.AutoSignInException
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Pending
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.views.BaseViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

/**
 * Implementation of [Navigator] and [UiActions].
 * It is based on activity view-model because instances of [Navigator] and [UiActions]
 * should be available from fragments' view-models (usually they are passed to the view-model constructor).
 *
 * This view-model extends [AndroidViewModel] which means that it is not "usual" view-model and
 * it may contain android dependencies (context, bundles, etc.).
 */
@HiltViewModel
class ActivityScopeViewModel @Inject constructor(
    private val userSettings: UserSettings,
    private val userRepository: IUserRepository,
) : BaseViewModel() {

    private val _authorizationStatus = MutableStateFlow<Result<Unit>>(Pending())
    val authorizationStatus: StateFlow<Result<Unit>> = _authorizationStatus

    private fun checkCurrentAuthToken() = viewModelScope.safeLaunch {
        if (userSettings.getCurrentRefreshToken() != null) {
            try {
                userRepository.refreshUserAccessToken()
            } catch (e: Exception) {
                val ex = AutoSignInException()
                val error = Error<Unit>(ex)
                _authorizationStatus.value = error
                return@safeLaunch
            }

            if (userSettings.getUserAccountAccessToken() != null) {
                _authorizationStatus.value = Success(value = Unit)
                return@safeLaunch
            }
        }
        val ex = AutoSignInException()
        val error = Error<Unit>(ex)
        _authorizationStatus.value = error
    }

    init {
        checkCurrentAuthToken()
    }
}