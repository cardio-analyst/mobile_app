package `is`.ulstu.cardioanalyst.ui.profile

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentProfileBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.app.AlertDialog
import android.graphics.Paint
import android.os.Bundle
import android.preference.DialogPreference
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class ProfileFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<ProfileViewModel>()

    private lateinit var binding: FragmentProfileBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        val allAvailableRegions = viewModel.getAllAvailableRegions()
        with(binding) {
            regionTextViewAlert.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.choose_region_text))
                builder.setItems(allAvailableRegions.toTypedArray()) { dialog, which ->
                    regionTextViewAlert.text = allAvailableRegions[which]
                }
                val dialog = builder.create()
                dialog.show()
            }

            val currentUserInfo = viewModel.getCurrentUser()
            emailTextEdit.setText(currentUserInfo.email)
            loginTextEdit.setText(currentUserInfo.login)
            nameTextEdit.setText("${currentUserInfo.lastName} ${currentUserInfo.firstName} ${currentUserInfo.middleName}")
            birthDateTextEdit.setText(currentUserInfo.birthDate)
            regionTextViewAlert.text = currentUserInfo.region

            exitButton.setOnClickListener {
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
            changeButton.setOnClickListener { changeMode(isChangingMode = true) }
            saveButton.setOnClickListener {
                viewModel.saveNewUserInfo(
                    email = emailTextEdit.text.toString(),
                    login = loginTextEdit.text.toString(),
                    password = passwordTextEdit.text.toString(),
                    name = nameTextEdit.text.toString(),
                    birthDate = birthDateTextEdit.text.toString(),
                    region = regionTextViewAlert.text.toString()
                )
                changeMode(isChangingMode = false)
            }
            cancelButton.setOnClickListener { changeMode(isChangingMode = false) }
        }
        return binding.root
    }

    private fun setUserInfo() {
        with(binding) {
            val currentUserInfo = viewModel.getCurrentUser()
            emailTextEdit.setText(currentUserInfo.email)
            loginTextEdit.setText(currentUserInfo.login)
            nameTextEdit.setText("${currentUserInfo.lastName} ${currentUserInfo.firstName} ${currentUserInfo.middleName}")
            birthDateTextEdit.setText(currentUserInfo.birthDate)
            regionTextViewAlert.text = currentUserInfo.region
            passwordTextEdit.text = null
        }
    }

    private fun changeMode(isChangingMode: Boolean = true) {
        with(binding) {
            if (isChangingMode) {
                exitButton.visibility = View.INVISIBLE
                changeButton.visibility = View.INVISIBLE
                saveButton.visibility = View.VISIBLE
                cancelButton.visibility = View.VISIBLE
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
                setUserInfo()

                exitButton.visibility = View.VISIBLE
                changeButton.visibility = View.VISIBLE
                saveButton.visibility = View.INVISIBLE
                cancelButton.visibility = View.INVISIBLE
                passwordTextView.visibility = View.INVISIBLE
                passwordTextEdit.visibility = View.INVISIBLE

                emailTextEdit.isEnabled = false
                loginTextEdit.isEnabled = false
                nameTextEdit.isEnabled = false
                birthDateTextEdit.isEnabled = false
                regionTextViewAlert.isEnabled = false
                passwordTextEdit.isEnabled = false

                emailTextEdit.paintFlags = emailTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                loginTextEdit.paintFlags = loginTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                nameTextEdit.paintFlags = nameTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                birthDateTextEdit.paintFlags =
                    birthDateTextEdit.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
                regionTextViewAlert.paintFlags =
                    regionTextViewAlert.paintFlags xor Paint.UNDERLINE_TEXT_FLAG
            }
        }
    }
}