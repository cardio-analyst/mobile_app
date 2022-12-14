package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentRegistrationBinding
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class RegistrationFragment @Inject constructor() : BaseFragment(R.layout.fragment_registration) {

    override val viewModel by viewModels<RegistrationViewModel>()

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

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
        viewModel.userSignUp.observeResults(
            this,
            binding.root,
            binding.resultView,
            {
                binding.resultView.setPendingDescription(resources.getString(R.string.flow_pending_auth))
                viewModel.reloadSignInUserRequest(
                    binding.loginTextEdit.text.toString(),
                    binding.passwordTextEdit.text.toString()
                )
                viewModel.onEnterNewUser(
                    binding.loginTextEdit.text.toString(),
                    binding.passwordTextEdit.text.toString()
                )
            },
            ignoreError = true,
            uiActions = viewModel.uiActions
        )
    }

    private fun observeUserSignIn() {
        viewModel.userSignIn.observeResults(
            this,
            binding.root,
            binding.resultView,
            { viewModel.onSuccessSignIn() },
            ignoreError = true,
            uiActions = viewModel.uiActions
        )
    }
}