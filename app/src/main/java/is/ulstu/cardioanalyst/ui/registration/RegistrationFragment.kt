package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentRegistrationBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter

class RegistrationFragment : BaseFragment() {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<RegistrationViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentRegistrationBinding.inflate(inflater, container, false)
        val allAvailableRegions = viewModel.getAllAvailableRegions()
        with(binding) {
            regionTextViewAlert.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.choose_region_text))
                builder.setItems(allAvailableRegions.toTypedArray()) {dialog, which ->
                    regionTextViewAlert.text = allAvailableRegions[which]
                }
                val dialog = builder.create()
                dialog.show()
            }
            registrationButton.setOnClickListener {
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
}