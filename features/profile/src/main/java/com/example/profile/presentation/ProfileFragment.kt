package com.example.profile.presentation

import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.BaseFragment
import com.example.presentation.ResultViewTools
import com.example.presentation.databinding.PairActionButtonsBinding
import com.example.presentation.observeResultsComponent
import com.example.profile.R
import com.example.profile.databinding.FragmentProfileBinding
import com.example.profile.domain.entities.UserData
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment @Inject constructor() : BaseFragment(R.layout.fragment_profile) {

    override val viewModel by viewModels<ProfileViewModel>()

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)
    private val resultViewTools by lazy {
        ResultViewTools(
            fragment = this,
            root = binding.root,
            resultView = binding.resultView,
            onSessionExpired = viewModel.onSessionExpired,
        )
    }
    private var changeModeState = false

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            // init region selector
            regionTextViewAlert.setOnClickListener {
                viewModel.regionsAlertDialogShow(context) { region ->
                    regionTextViewAlert.text = region
                }
            }

            // init user information fields
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_info))
            resultView.setTryAgainAction { viewModel.getOrReloadGetCurrentUser() }
            editButton.setOnClickListener {
                changeMode()
            }
            exitButton.setOnClickListener {
                AlertDialog.Builder(this@ProfileFragment.context)
                    .setTitle(R.string.exit_account_alert_dialog_title)
                    .setMessage(R.string.exit_account_alert_dialog_message)
                    .setPositiveButton(android.R.string.yes) { _, _ ->
                        viewModel.onExitClick()
                    }
                    .setNegativeButton(android.R.string.no) { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            }

            // init buttons logic
            with(actionButtonsBinding) {
                negativeButton.visibility = View.INVISIBLE
                positiveButton.visibility = View.INVISIBLE
                negativeButton.setOnClickListener {
                    changeMode()
                    viewModel.getOrReloadGetCurrentUser()
                }
                positiveButton.setOnClickListener {
                    with(binding) {
                        val userData = UserData(
                            email = emailTextEdit.text.toString(),
                            login = loginTextEdit.text.toString(),
                            password = passwordTextEdit.text.toString(),
                            name = nameTextEdit.text.toString(),
                            birthDate = birthDateTextEdit.text.toString(),
                            region = regionTextViewAlert.text.toString()
                        )
                        viewModel.saveNewUserInfo(userData)
                        changeMode()
                    }
                }
            }
        }

        observeUserDetails()
        viewModel.getOrReloadGetCurrentUser()
    }

    private fun observeUserDetails() {
        viewModel.user.observeResultsComponent(resultViewTools) { currentUserInfo ->
            with(binding) {
                emailTextEdit.setText(currentUserInfo.email)
                loginTextEdit.setText(currentUserInfo.login)
                nameTextEdit.setText(
                    "${currentUserInfo.lastName} " +
                            "${currentUserInfo.firstName} ${currentUserInfo.middleName}"
                )
                birthDateTextEdit.setText(currentUserInfo.birthDate)
                regionTextViewAlert.text = currentUserInfo.region
                changeMode()
                changeMode()
            }
        }
    }

    private fun changeMode() {
        changeModeState = !changeModeState
        with(binding) {
            exitButton.visibility = if (!changeModeState) View.VISIBLE else View.INVISIBLE
            passwordTextView.visibility = if (changeModeState) View.VISIBLE else View.INVISIBLE
            passwordTextEdit.visibility = if (changeModeState) View.VISIBLE else View.INVISIBLE
            with(actionButtonsBinding) {
                negativeButton.visibility = if (changeModeState) View.VISIBLE else View.INVISIBLE
                positiveButton.visibility = if (changeModeState) View.VISIBLE else View.INVISIBLE
            }

            emailTextEdit.isEnabled = changeModeState
            loginTextEdit.isEnabled = changeModeState
            nameTextEdit.isEnabled = changeModeState
            birthDateTextEdit.isEnabled = changeModeState
            regionTextViewAlert.isEnabled = changeModeState
            passwordTextEdit.isEnabled = changeModeState

            if (changeModeState) {
                emailTextEdit.paintFlags = emailTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                loginTextEdit.paintFlags = loginTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                nameTextEdit.paintFlags = nameTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                birthDateTextEdit.paintFlags =
                    birthDateTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                regionTextViewAlert.paintFlags =
                    regionTextViewAlert.paintFlags or Paint.UNDERLINE_TEXT_FLAG
            } else {
                emailTextEdit.paintFlags =
                    emailTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                loginTextEdit.paintFlags =
                    loginTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                nameTextEdit.paintFlags = nameTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                birthDateTextEdit.paintFlags =
                    birthDateTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                regionTextViewAlert.paintFlags =
                    regionTextViewAlert.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onCleared()
    }
}