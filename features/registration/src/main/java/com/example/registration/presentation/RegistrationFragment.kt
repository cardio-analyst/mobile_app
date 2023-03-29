package com.example.registration.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.BaseFragment
import com.example.presentation.ResultViewTools
import com.example.presentation.observeResultsComponent
import com.example.registration.R
import com.example.registration.databinding.FragmentRegistrationBinding
import com.example.registration.domain.entities.UserData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment @Inject constructor() : BaseFragment(R.layout.fragment_registration) {

    override val viewModel by viewModels<RegistrationViewModel>()

    private val binding by viewBinding(FragmentRegistrationBinding::bind)
    private val resultViewTools by lazy {
        ResultViewTools(
            fragment = this,
            root = binding.root,
            resultView = binding.resultView,
            ignoreError = true,
            uiActions = viewModel.uiActions,
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            observeUserSignUp()
            observeUserSignIn()
            regionTextViewAlert.setOnClickListener {
                viewModel.regionsAlertDialogShow(context) { region ->
                    regionTextViewAlert.text = region
                }
            }
            registrationButton.setOnClickListener {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_reg))
                val userData = UserData(
                    email = emailTextEdit.text.toString(),
                    login = loginTextEdit.text.toString(),
                    password = passwordTextEdit.text.toString(),
                    name = nameTextEdit.text.toString(),
                    birthDate = birthDateTextEdit.text.toString(),
                    region = regionTextViewAlert.text.toString()
                )
                viewModel.reloadSignUpUserRequest(userData)
                viewModel.onRegisterNewUser(userData)
            }
        }
    }

    private fun observeUserSignUp() {
        viewModel.userSignUp.observeResultsComponent(
            resultViewTools = resultViewTools,
            onSessionExpired = null,
        ) {
            with(binding) {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_auth))
                viewModel.reloadSignInUserRequest(
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString()
                )
                viewModel.onEnterNewUser(
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString()
                )
            }
        }
    }

    private fun observeUserSignIn() {
        viewModel.userSignIn.observeResultsComponent(
            resultViewTools = resultViewTools,
            onSessionExpired = null,
        ) {
            viewModel.launchTabsScreen()
        }
    }
}