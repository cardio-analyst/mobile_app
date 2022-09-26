package `is`.ulstu.cardioanalyst.ui.registration

import `is`.ulstu.cardioanalyst.databinding.FragmentRegistrationBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
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
        val regionAdapter = this.context?.let {
            ArrayAdapter(
                it,
                android.R.layout.simple_spinner_dropdown_item,
                allAvailableRegions
            )
        }
        with(binding) {
            regionSpinner.adapter = regionAdapter
            /*regionSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {

                }

                override fun onNothingSelected(p0: AdapterView<*>?) {}
            }*/
            registrationButton.setOnClickListener {
                viewModel.onRegisterNewUser(
                    loginTextEdit.text.toString(),
                    passwordTextEdit.text.toString(),
                    nameTextEdit.text.toString(),
                    allAvailableRegions[regionSpinner.selectedItemPosition]
                )
            }
        }
        return binding.root
    }
}