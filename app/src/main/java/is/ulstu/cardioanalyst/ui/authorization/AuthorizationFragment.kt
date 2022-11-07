package `is`.ulstu.cardioanalyst.ui.authorization

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentAuthorizationBinding
import `is`.ulstu.cardioanalyst.databinding.FragmentLaboratoryResearchBinding
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import by.kirich1409.viewbindingdelegate.viewBinding

class AuthorizationFragment : BaseFragment(R.layout.fragment_authorization) {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<AuthorizationViewModel>()

    private val binding by viewBinding(FragmentAuthorizationBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.checkCurrentAuthToken()
        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_auth))
            observeUserSignIn()
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
            registrationButton.setOnClickListener { viewModel.onRegister() }
        }
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