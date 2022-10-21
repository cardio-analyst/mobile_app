package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserSignUpResponseEntity
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope

class RegistrationViewModel(
    private val navigator: Navigator,
    val uiActions: UiActions,
) : BaseViewModel(navigator, uiActions) {

    private val userRepository: IUserRepository = Singletons.userRepository

    private val _userSignUp = MutableLiveData<Result<UserSignUpResponseEntity>>()
    val userSignUp = _userSignUp.share()

    private val _userSignIn = MutableLiveData<Result<Unit>>()
    val userSignIn = _userSignIn.share()

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
        ).collect {
            _userSignUp.value = it
        }
    }

    fun reloadSignInUserRequest(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)

    fun reloadSignUpUserRequest(
        email: String,
        login: String,
        password: String,
        name: String,
        birthDate: String,
        region: String
    ) {
        val fullName = name.split(' ').toList()
        userRepository.reloadSignUpUserRequest(
            login,
            email,
            password,
            fullName[1],
            fullName[0],
            fullName[2],
            birthDate,
            region
        )
    }

    fun onEnterNewUser(login: String, password: String) = viewModelScope.safeLaunch {
        userRepository.signInUser(login, password).collect {
            _userSignIn.value = it
        }
    }

    fun onSuccessSignIn() {
        // clear stack?
        navigator.goBack()
        if (Singletons.appSettings.getCurrentRefreshToken() != null)
            navigator.addFragmentToScreen(R.id.fragmentContainer, NavigationFragment.Screen())
    }
}