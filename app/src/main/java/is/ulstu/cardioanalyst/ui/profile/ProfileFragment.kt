package `is`.ulstu.cardioanalyst.ui.profile

import `is`.ulstu.cardioanalyst.databinding.FragmentProfileBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProfileFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<ProfileViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentProfileBinding.inflate(inflater, container, false)
        with(binding) {
            val currentUserInfo = viewModel.getCurrentUser()
            emailTextEdit.setText(currentUserInfo.email)
            loginTextEdit.setText(currentUserInfo.login)
            nameTextEdit.setText("${currentUserInfo.lastName} ${currentUserInfo.firstName} ${currentUserInfo.middleName}")
            birthDateTextEdit.setText(currentUserInfo.birthDate)
            regionTextViewAlert.text = currentUserInfo.region
        }
        return binding.root
    }
}