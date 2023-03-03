package `is`.ulstu.cardioanalyst.ui.authorization

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentAuthorizationBinding
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
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
                navigateToRegistrationScreen()
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
                navigateToTabsScreen()
            },
            ignoreError = true,
            uiActions = viewModel.uiActions
        )
    }

    private fun navigateToRegistrationScreen() {
        val direction =
            AuthorizationFragmentDirections.actionAuthorizationFragmentToRegistrationFragment()
        findNavController().navigate(direction)
    }

    private fun navigateToTabsScreen() {
        val direction =
            AuthorizationFragmentDirections.actionAuthorizationFragmentToTabsFragment()
        findNavController().navigate(direction)
    }

}