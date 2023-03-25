package `is`.ulstu.cardioanalyst.ui.report

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewModelScope
import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.common.IncorrectEmailException
import com.example.common.RefreshTokenExpired
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.common.flows.Success
import com.example.data.repositories.recommendations.IRecommendationsDataRepository
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseEntity
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Const
import `is`.ulstu.cardioanalyst.ui.profile.ProfileFragment
import javax.inject.Inject

@HiltViewModel
class SendingReportViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val recommendationsRepository: IRecommendationsDataRepository,
    private val profileFragment: ProfileFragment,
) : BaseViewModel(uiActions) {

    private val _sendReportToEmail = SingleLiveEvent<ResultState<SendReportResponseEntity>>()

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

    fun observeSendReportToEmail(lifecycleOwner: LifecycleOwner) {
        _sendReportToEmail.observe(lifecycleOwner) { result ->
            if (result is Success) {
                uiActions.toast(R.string.report_sent_toast)
            }
            if (result is Error) {
                val message = when (result.error) {
                    is ConnectionException -> result.error.message
                    is BackendExceptions -> (result.error as BackendExceptions).description
                    else -> uiActions.getString(R.string.unknown_exception)
                }
                message?.let { uiActions.toast(it) }
            }
        }
    }

}