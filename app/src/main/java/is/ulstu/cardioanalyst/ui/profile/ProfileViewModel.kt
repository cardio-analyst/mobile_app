package `is`.ulstu.cardioanalyst.ui.profile

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserInfoRequestEntity
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserInfoResponseEntity
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserSingUpRequestEntity
import `is`.ulstu.cardioanalyst.ui.authorization.AuthorizationFragment
import `is`.ulstu.cardioanalyst.ui.registration.UserData
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope


class ProfileViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val userRepository: IUserRepository = Singletons.userRepository

    private val _user = MutableLiveData<Result<UserInfoResponseEntity>>()
    val user = _user.share()

    private fun getAllAvailableRegions() = userRepository.getAllAvailableRegions()

    private fun getCurrentUser() = viewModelScope.safeLaunch {
        userRepository.getCurrentUserInfo().collect {
            if (it is Error && it.error is RefreshTokenExpired)
                throw it.error
            _user.value = it
        }
    }

    fun reload() {
        userRepository.reloadCurrentUserInfo()
    }

    fun saveNewUserInfo(userData: UserData) = viewModelScope.safeLaunch {
        val userInfoRequestEntity = validateUserInfo(userData)
        userRepository.changeUserParams(userInfoRequestEntity)
        uiActions.toast(Singletons.getString(R.string.user_info_save))
    }

    fun onExitClick() = viewModelScope.safeLaunch {
        userRepository.logoutUser()
        navigator.addFragmentToScreen(R.id.fragmentContainer, AuthorizationFragment.Screen())
    }

    fun regionsAlertDialogShow(context: Context?, action: (region: String) -> Unit) {
        val regions =
            getAllAvailableRegions().toTypedArray()
        AlertDialog.Builder(context)
            .setTitle(Singletons.getString(R.string.choose_region_text))
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

    init {
        getCurrentUser()
    }
}