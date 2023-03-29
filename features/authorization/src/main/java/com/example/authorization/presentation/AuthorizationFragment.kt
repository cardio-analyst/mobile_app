package com.example.authorization.presentation

import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.authorization.R
import com.example.authorization.databinding.FragmentAuthorizationBinding
import com.example.presentation.BaseFragment
import com.example.presentation.observeResults
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class AuthorizationFragment @Inject constructor() : BaseFragment(R.layout.fragment_authorization) {

    override val viewModel by viewModels<AuthorizationViewModel>()

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_auth))
            enterButton.setOnClickListener {
                viewModel.reload(
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString()
                )
                viewModel.onEnter(
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString()
                )
            }
            registrationButton.setOnClickListener {
                viewModel.launchRegistrationScreen()
            }
        }

        observeUserSignIn()
    }

    private fun observeUserSignIn() {
        viewModel.userSignIn.observeResults(
            this,
            binding.root,
            binding.resultView,
            {
                viewModel.launchTabsScreen()
            },
            ignoreError = true,
            uiActions = viewModel.uiActions
        )
    }

}