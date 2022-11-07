package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentRegistrationBinding
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import by.kirich1409.viewbindingdelegate.viewBinding

class RegistrationFragment : BaseFragment(R.layout.fragment_registration) {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<RegistrationViewModel>()

    private val binding by viewBinding(FragmentRegistrationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            observeUserSignUp()
            observeUserSignIn()
            regionTextViewAlert.setOnClickListener {
                val regions =
                    viewModel.getAllAvailableRegions()?.toTypedArray() ?: return@setOnClickListener
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.choose_region_text))
                builder.setItems(regions) { dialog, which ->
                    regionTextViewAlert.text = regions[which]
                }
                val dialog = builder.create()
                dialog.show()
            }
            registrationButton.setOnClickListener {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_reg))
                viewModel.reloadSignUpUserRequest(
                    emailTextEdit.text.toString(),
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString(),
                    nameTextEdit.text.toString(),
                    birthDateTextEdit.text.toString(),
                    regionTextViewAlert.text.toString()
                )
                viewModel.onRegisterNewUser(
                    emailTextEdit.text.toString(),
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString(),
                    nameTextEdit.text.toString(),
                    birthDateTextEdit.text.toString(),
                    regionTextViewAlert.text.toString()
                )
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