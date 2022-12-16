package `is`.ulstu.cardioanalyst.ui.profile

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentProfileBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.cardioanalyst.ui.registration.UserData
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class ProfileFragment @Inject constructor() : BaseFragment(R.layout.fragment_profile) {

    override val viewModel by viewModels<ProfileViewModel>()

    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)

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
            shareButton.setOnClickListener { viewModel.sendReportToEmail() }
        }

        observeUserDetails()
        viewModel.getOrReloadGetCurrentUser()
    }
    private fun observeUserDetails() {
        viewModel.user.observeResults(this, binding.root, binding.resultView, { currentUserInfo ->
            with(binding) {
                emailTextEdit.setText(currentUserInfo.email)
                loginTextEdit.setText(currentUserInfo.login)
                nameTextEdit.setText(
                    "${currentUserInfo.lastName} " +
                            "${currentUserInfo.firstName} ${currentUserInfo.middleName}"
                )
                birthDateTextEdit.setText(currentUserInfo.birthDate)
                regionTextViewAlert.text = currentUserInfo.region
                changeMode(true)
                changeMode(false)
            }
        })
    }

    private fun changeMode(isChangingMode: Boolean = true) {
        with(binding) {
            if (isChangingMode) {
                // init buttons logic
                with(actionButtonsBinding) {
                    negativeButton.text = resources.getString(R.string.button_text_cancel)
                    positiveButton.text = resources.getString(R.string.button_text_save)
                    negativeButton.setOnClickListener {
                        changeMode(isChangingMode = false)
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
                            changeMode(isChangingMode = false)
                        }
                    }
                }

                passwordTextView.visibility = View.VISIBLE
                passwordTextEdit.visibility = View.VISIBLE

                emailTextEdit.isEnabled = true
                loginTextEdit.isEnabled = true
                nameTextEdit.isEnabled = true
                birthDateTextEdit.isEnabled = true
                regionTextViewAlert.isEnabled = true
                passwordTextEdit.isEnabled = true

                emailTextEdit.paintFlags = emailTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                loginTextEdit.paintFlags = loginTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                nameTextEdit.paintFlags = nameTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                birthDateTextEdit.paintFlags =
                    birthDateTextEdit.paintFlags or Paint.UNDERLINE_TEXT_FLAG
                regionTextViewAlert.paintFlags =
                    regionTextViewAlert.paintFlags or Paint.UNDERLINE_TEXT_FLAG

            } else {
                // init buttons logic
                with(actionButtonsBinding) {
                    negativeButton.text = resources.getString(R.string.button_text_exit)
                    positiveButton.text = resources.getString(R.string.button_text_change)
                    negativeButton.setOnClickListener {
                        AlertDialog.Builder(this@ProfileFragment.context)
                            .setTitle(R.string.exit_account_alert_dialog_title)
                            .setMessage(R.string.exit_account_alert_dialog_message)
                            .setPositiveButton(android.R.string.yes) { dialog, _ ->
                                viewModel.onExitClick()
                            }
                            .setNegativeButton(android.R.string.no) { dialog, _ ->
                                dialog.dismiss()
                            }
                            .create()
                            .show()
                    }
                    positiveButton.setOnClickListener { changeMode(isChangingMode = true) }
                }

                passwordTextView.visibility = View.INVISIBLE
                passwordTextEdit.visibility = View.INVISIBLE

                emailTextEdit.isEnabled = false
                loginTextEdit.isEnabled = false
                nameTextEdit.isEnabled = false
                birthDateTextEdit.isEnabled = false
                regionTextViewAlert.isEnabled = false
                passwordTextEdit.isEnabled = false

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