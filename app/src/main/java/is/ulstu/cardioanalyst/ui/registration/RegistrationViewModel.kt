package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay

class RegistrationViewModel(
    private val navigator: Navigator,
    private val uiActions: UiActions,
) : BaseViewModel(uiActions) {

    private val userRepository: IUserRepository = Singletons.userRepository

    fun getAllAvailableRegions() = viewModelScope.safeLaunchData {
        userRepository.getAllAvailableRegions()
    }

    fun onRegisterNewUser(
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
        if (password.length < 7)
            throw IncorrectPasswordException()

        userRepository.signUpUser(
            login,
            email,
            password,
            fullName[1],
            fullName[0],
            fullName[2],
            birthDate,
            region
        )
        delay(500)
        userRepository.singInUser(login, password)

        // on Success
        uiActions.toast(Singletons.getString(R.string.user_sign_up_successful))
        navigator.goBack()
        if (Singletons.appSettings.getCurrentToken() != null)
            navigator.addFragmentToScreen(R.id.fragmentContainer, NavigationFragment.Screen())
    }
}