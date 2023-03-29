package com.example.registration.presentation

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.*
import com.example.common.constants.RegexConstants
import com.example.common.flows.ResultState
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import com.example.registration.domain.UserSignUpRepository
import com.example.registration.domain.entities.UserData
import com.example.registration.domain.entities.UserSignUpResponseEntity
import com.example.registration.domain.entities.UserSingUpRequestEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationViewModel @Inject constructor(
    val uiActions: UiAction,
    private val userSignUpRepository: UserSignUpRepository,
    private val registrationRouter: RegistrationRouter,
) : BaseViewModel(uiActions), RegistrationRouter by registrationRouter {

    private val _userSignUp = MutableLiveData<ResultState<UserSignUpResponseEntity>>()
    val userSignUp = _userSignUp.share()

    private val _userSignIn = MutableLiveData<ResultState<Unit>>()
    val userSignIn = _userSignIn.share()

    private fun getAllAvailableRegions() = userSignUpRepository.getAllAvailableRegions()

    fun onRegisterNewUser(userData: UserData) = viewModelScope.safeLaunch {
        val userSingUpRequestEntity = validateUserInfo(userData)
        userSignUpRepository.signUpUser(userSingUpRequestEntity).collect {
            _userSignUp.value = it
        }
    }

    fun reloadSignInUserRequest(loginOrEmail: String, password: String) =
        userSignUpRepository.reloadSignInUserRequest(loginOrEmail, password)

    fun reloadSignUpUserRequest(userData: UserData) {
        try {
            val userSingUpRequestEntity = validateUserInfo(userData)
            userSignUpRepository.reloadSignUpUserRequest(userSingUpRequestEntity)
        } catch (_: Exception) {

        }
    }

    fun onEnterNewUser(login: String, password: String) = viewModelScope.safeLaunch {
        userSignUpRepository.signInUser(login, password).collect {
            _userSignIn.value = it
        }
    }

    fun regionsAlertDialogShow(context: Context?, action: (region: String) -> Unit) {
        val regions =
            getAllAvailableRegions().toTypedArray()
        AlertDialog.Builder(context)
            .setTitle(uiActions.getString(com.example.registration.R.string.choose_region_text))
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
        val regexEmail = Regex(RegexConstants.REGEX_EMAIL)
        val regexDate = Regex(RegexConstants.REGEX_DATE)

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