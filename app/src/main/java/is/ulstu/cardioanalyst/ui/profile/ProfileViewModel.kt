package `is`.ulstu.cardioanalyst.ui.profile

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.ui.authorization.AuthorizationFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope

class ProfileViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel() {

    private val userRepository: IUserRepository = Singletons.userRepository

    fun getAllAvailableRegions() = viewModelScope.safeLaunchData {
        userRepository.getAllAvailableRegions()
    }

    fun getCurrentUser() = viewModelScope.safeLaunchData {
        userRepository.getCurrentUserInfo()
    }

    fun saveNewUserInfo(
        email: String,
        login: String,
        password: String,
        name: String,
        birthDate: String,
        region: String
    ) = viewModelScope.safeLaunch {
        val regexEmail = Regex(Const.REGEX_EMAIL)
        val regexDate = Regex(Const.REGEX_DATE)

        val fullName = name.split(' ').toList()
        if (!regexEmail.matches(email))
            throw IncorrectEmailException()
        if (fullName.size != 3)
            throw IncorrectFullNameException()
        if (!regexDate.matches(birthDate))
            throw IncorrectBirthDateException()
        if (region == "")
            throw IncorrectRegionException()
        if (password != "" && password.length < 7)
            throw IncorrectPasswordException()
        userRepository.changeUserParams(
            login,
            email,
            fullName[1],
            fullName[0],
            fullName[2],
            birthDate,
            region,
            password
        )
        uiActions.toast(Singletons.getString(R.string.user_info_save))
    }

    fun onExitClick() = viewModelScope.safeLaunch {
        userRepository.logoutUser()
        navigator.addFragmentToScreen(R.id.fragmentContainer, AuthorizationFragment.Screen())
    }
}