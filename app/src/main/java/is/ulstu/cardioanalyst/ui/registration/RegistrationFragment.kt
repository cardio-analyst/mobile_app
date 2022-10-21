package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentRegistrationBinding
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class RegistrationFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<RegistrationViewModel>()

    private lateinit var binding: FragmentRegistrationBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        with(binding) {
            observeUserSignUp()
            observeUserSignIn()
            regionTextViewAlert.setOnClickListener {
                val regions = viewModel.getAllAvailableRegions()?.toTypedArray() ?: return@setOnClickListener
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
        return binding.root
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