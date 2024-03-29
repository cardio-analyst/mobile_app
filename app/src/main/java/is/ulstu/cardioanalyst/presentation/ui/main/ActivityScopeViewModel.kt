package `is`.ulstu.cardioanalyst.presentation.ui.main

import androidx.lifecycle.AndroidViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import com.example.data.settings.UserSettings
import com.example.presentation.BaseViewModel
import javax.inject.Inject

/**
 * This view-model extends [AndroidViewModel] which means that it is not "usual" view-model and
 * it may contain android dependencies (context, bundles, etc.).
 */
@HiltViewModel
class ActivityScopeViewModel @Inject constructor(
    private val userSettings: UserSettings,
) : BaseViewModel() {

    fun isUserAuthorized() = !userSettings.getCurrentRefreshToken().isNullOrEmpty()

}