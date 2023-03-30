package com.example.profile.presentation

import android.app.AlertDialog
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.common.*
import com.example.common.constants.RegexConstants
import com.example.common.flows.Error
import com.example.common.flows.ResultState
import com.example.presentation.BaseViewModel
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import com.example.profile.domain.UserInfoRepository
import com.example.profile.domain.entities.UserData
import com.example.profile.domain.entities.UserInfoRequestEntity
import com.example.profile.domain.entities.UserInfoResponseEntity
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val uiActions: UiAction,
    private val userRepository: UserInfoRepository,
    private val profileRouter: ProfileRouter,
) : BaseViewModel(uiActions), ProfileRouter by profileRouter {

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
        uiActions.toast(com.example.profile.R.string.user_info_save)
    }

    fun onExitClick() = viewModelScope.safeLaunch {
        userRepository.logoutUser()
        launchAuthorizationScreen()
        /*delay(500)
        exitProcess(-1)*/
    }

    fun regionsAlertDialogShow(context: Context?, action: (region: String) -> Unit) {
        val regions =
            getAllAvailableRegions().toTypedArray()
        AlertDialog.Builder(context)
            .setTitle(uiActions.getString(com.example.profile.R.string.choose_region_text))
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
        val regexEmail = Regex(RegexConstants.REGEX_EMAIL)
        val regexDate = Regex(RegexConstants.REGEX_DATE)

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