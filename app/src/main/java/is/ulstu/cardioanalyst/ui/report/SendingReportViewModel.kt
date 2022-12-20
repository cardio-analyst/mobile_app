package `is`.ulstu.cardioanalyst.ui.report

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.recommendations.IRecommendationsRepository
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportRequestEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportResponseEntity
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.ui.profile.ProfileFragment
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.SingleLiveEvent
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SendingReportViewModel @Inject constructor(
    private val navigator: Navigator,
    userSettings: UserSettings,
    private val uiActions: UiActions,
    private val recommendationsRepository: IRecommendationsRepository,
    private val profileFragment: ProfileFragment,
) : BaseViewModel(navigator, userSettings, uiActions) {

    private val _sendReportToEmail = SingleLiveEvent<Result<SendReportResponseEntity>>()

    fun sendReportToEmail(receiver: String, isSendToUserEmail: Boolean) {
        viewModelScope.safeLaunch {
            // validation
            if (receiver != "") {
                val regexEmail = Regex(Const.REGEX_EMAIL)
                if (!regexEmail.matches(receiver))
                    throw IncorrectEmailException()
            } else if (!isSendToUserEmail) {
                throw IncorrectEmailException()
            }


            recommendationsRepository.sendReportToEmail(
                SendReportRequestEntity(receiver, isSendToUserEmail)
            ).collect {
                if (it is Error && it.error is RefreshTokenExpired)
                    throw it.error
                _sendReportToEmail.value = it
            }
        }
    }

    fun close() = navigator.addFragmentToScreen(R.id.tabFragmentContainer, profileFragment)

    fun observeSendReportToEmail(lifecycleOwner: LifecycleOwner) {
        _sendReportToEmail.observe(lifecycleOwner) { result ->
            if (result is Success) {
                uiActions.toast(R.string.report_sent_toast)
                close()
            }
            if (result is Error) {
                val message = when (result.error) {
                    is ConnectionException -> result.error.message
                    is BackendExceptions -> result.error.description
                    else -> uiActions.getString(R.string.unknown_exception)
                }
                message?.let { uiActions.toast(it) }
            }
        }
    }

}