package com.example.authorization.presentation

import androidx.lifecycle.viewModelScope
import com.example.authorization.domain.UserSignInRepository
import com.example.common.flows.ResultState
import com.example.presentation.BaseViewModel
import com.example.presentation.SingleLiveEvent
import com.example.presentation.share
import com.example.presentation.uiactions.UiAction
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AuthorizationViewModel @Inject constructor(
    val uiActions: UiAction,
    private val userSignInRepository: UserSignInRepository,
    private val authorizationRouter: AuthorizationRouter,
) : BaseViewModel(uiActions), AuthorizationRouter by authorizationRouter {

    private val _userSignIn = SingleLiveEvent<ResultState<Unit>>()
    val userSignIn = _userSignIn.share()

    fun onEnter(loginOrEmail: String, password: String) = viewModelScope.safeLaunch {
        userSignInRepository.signInUser(loginOrEmail, password).collect {
            _userSignIn.value = it
        }
    }

    fun reload(loginOrEmail: String, password: String) =
        userSignInRepository.reloadSignInUserRequest(loginOrEmail, password)
}