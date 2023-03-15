package `is`.ulstu.cardioanalyst.ui.profile

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.*
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.presentation.share
import dagger.hilt.android.lifecycle.HiltViewModel
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Const
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserInfoRequestEntity
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserInfoResponseEntity
import `is`.ulstu.cardioanalyst.ui.registration.UserData
import `is`.ulstu.cardioanalyst.ui.report.SendingReportFragment
import com.example.presentation.uiactions.UiAction
import com.example.presentation.BaseViewModel
import kotlinx.coroutines.delay
import javax.inject.Inject
import kotlin.system.exitProcess

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val userRepository: IUserRepository,
    private val sendingReportFragment: SendingReportFragment,
) : BaseViewModel(uiActions) {

    private val _user = MutableLiveData<ResultState<UserInfoResponseEntity>>()
    val user = _user.share()

    private fun getAllAvailableRegions() = userRepository.getAllAvailableRegions()

    private fun getCurrentUser() = viewModelScope.safeLaunch {
        userRepository.getCurrentUserInfo().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _user.value = it
        }
    }

    fun getOrReloadGetCurrentUser() =
        if (firstLoadFlag)
            getCurrentUser()
        else
            userRepository.reloadCurrentUserInfo()

    fun saveNewUserInfo(userData: UserData) = viewModelScope.safeLaunch {
        val userInfoRequestEntity = validateUserInfo(userData)
        userRepository.changeUserParams(userInfoRequestEntity)
        uiActions.toast(R.string.user_info_save)
    }

    fun onExitClick() = viewModelScope.safeLaunch {
        userRepository.logoutUser()
        delay(500)
        exitProcess(-1)
    }

    fun regionsAlertDialogShow(context: Context?, action: (region: String) -> Unit) {
        val regions =
            getAllAvailableRegions().toTypedArray()
        AlertDialog.Builder(context)
            .setTitle(uiActions.getString(R.string.choose_region_text))
            .setItems(regions) { _, which ->
                action(regions[which])
            }
            .create()
            .show()
    }

    /**
     * @throws IncorrectEmailException
     * @throws IncorrectFullNameException
     * @throws IncorrectBirthDateException
     * @throws IncorrectRegionException
     * @throws IncorrectPasswordException
     */
    private fun validateUserInfo(userData: UserData): UserInfoRequestEntity {
        val regexEmail = Regex(Const.REGEX_EMAIL)
        val regexDate = Regex(Const.REGEX_DATE)

        val fullName = userData.name.split(' ').toList()
        when {
            !regexEmail.matches(userData.email) -> throw IncorrectEmailException()
            fullName.size != 3 -> throw IncorrectFullNameException()
            !regexDate.matches(userData.birthDate) -> throw IncorrectBirthDateException()
            userData.region == "" -> throw IncorrectRegionException()
            (userData.password != "" && userData.password.length < 7) ->
                throw IncorrectPasswordException()
        }

        return UserInfoRequestEntity(
            firstName = fullName[1],
            lastName = fullName[0],
            middleName = fullName[2],
            birthDate = userData.birthDate,
            region = userData.region,
            email = userData.email,
            login = userData.login,
            password = userData.password,
        )
    }

}