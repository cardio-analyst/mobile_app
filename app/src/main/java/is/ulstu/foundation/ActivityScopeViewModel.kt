package `is`.ulstu.foundation

import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
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
) : BaseViewModel() {

    fun isUserAuthorized() = !userSettings.getCurrentRefreshToken().isNullOrEmpty()

}