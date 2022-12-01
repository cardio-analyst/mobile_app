package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.*
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserSignUpResponseEntity
import `is`.ulstu.cardioanalyst.models.users.sources.entities.UserSingUpRequestEntity
import `is`.ulstu.cardioanalyst.ui.navigation.NavigationFragment
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.uiactions.UiActions
import `is`.ulstu.foundation.utils.share
import `is`.ulstu.foundation.views.BaseViewModel
import android.app.AlertDialog
import android.content.Context
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

    private fun getAllAvailableRegions() = userRepository.getAllAvailableRegions()

    fun onRegisterNewUser(userData: UserData) = viewModelScope.safeLaunch {
        val userSingUpRequestEntity = validateUserInfo(userData)
        userRepository.signUpUser(userSingUpRequestEntity).collect {
            _userSignUp.value = it
        }
    }

    fun reloadSignInUserRequest(loginOrEmail: String, password: String) =
        userRepository.reloadSignInUserRequest(loginOrEmail, password)

    fun reloadSignUpUserRequest(userData: UserData) {
        try {
            val userSingUpRequestEntity = validateUserInfo(userData)
            userRepository.reloadSignUpUserRequest(userSingUpRequestEntity)
        } catch (_: Exception) {
        }
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
    private fun validateUserInfo(userData: UserData): UserSingUpRequestEntity {
        val regexEmail = Regex(Const.REGEX_EMAIL)
        val regexDate = Regex(Const.REGEX_DATE)

        val fullName = userData.name.split(' ').toList()
        when {
            !regexEmail.matches(userData.email) -> throw IncorrectEmailException()
            fullName.size != 3 -> throw IncorrectFullNameException()
            !regexDate.matches(userData.birthDate) -> throw IncorrectBirthDateException()
            userData.region == "" -> throw IncorrectRegionException()
            userData.password.length < 7 -> throw IncorrectPasswordException()
        }

        return UserSingUpRequestEntity(
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