package com.example.lifestyle.presentation

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.TextView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.lifestyle.R
import com.example.lifestyle.databinding.FragmentLifestyleBinding
import com.example.lifestyle.domain.entities.LifestyleEntity
import com.example.presentation.BaseFragment
import com.example.presentation.ResultViewTools
import com.example.presentation.databinding.PairActionButtonsBinding
import com.example.presentation.observeResultsComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LifestyleFragment @Inject constructor() : BaseFragment(R.layout.fragment_lifestyle) {

    override val viewModel by viewModels<LifestyleViewModel>()

    private val binding by viewBinding(FragmentLifestyleBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)
    private val resultViewTools by lazy {
        ResultViewTools(
            fragment = this,
            root = binding.root,
            resultView = binding.resultView,
            onSessionExpired = viewModel.onSessionExpired,
        )
    }
    private lateinit var buttonVisibility: (visibility: Int) -> Unit

    private lateinit var currentLifestyleData: LifestyleEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(actionButtonsBinding) {
            buttonVisibility = {
                negativeButton.visibility = it
                positiveButton.visibility = it
            }
            buttonVisibility(View.INVISIBLE)
        }

        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_lifestyle_load))
            resultView.setTryAgainAction { viewModel.getOrReloadLifestyle() }
        }

        observeLifestyle()
        observeLifestyleSave()
        viewModel.getOrReloadLifestyle()
    }

    private fun observeLifestyle() {
        viewModel.lifestyle.observeResultsComponent(resultViewTools) { data ->
            // init views
            currentLifestyleData = data.copy()
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
            resetViews(currentLifestyleData, data)

            with(actionButtonsBinding) {
                negativeButton.setOnClickListener {
                    currentLifestyleData = data.copy()
                    resetViews(currentLifestyleData, data)
                    buttonVisibility(View.INVISIBLE)
                }
                positiveButton.setOnClickListener {
                    with(binding) {
                        resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_lifestyle_save))
                        resultView.setTryAgainAction {
                            viewModel.reloadLifestyleSave(
                                currentLifestyleData
                            )
                        }
                    }
                    viewModel.setUserLifestyle(currentLifestyleData)
                    buttonVisibility(View.INVISIBLE)
                }
            }
        }
    }

    private fun observeLifestyleSave() {
        viewModel.lifestyleSave.observeResultsComponent(resultViewTools) {
            viewModel.onSuccessSaveToast()
            with(binding) {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_lifestyle_load))
                resultView.setTryAgainAction { viewModel.getOrReloadLifestyle() }
            }
        }
    }

    private fun resetViews(currentLifestyleData: LifestyleEntity, data: LifestyleEntity) {

        // clear views
        binding.lifestyleLinearLayout.removeAllViews()
        val actualData = if (currentLifestyleData == data) data else currentLifestyleData

        // Family status
        addLifestyleFieldChooseView(
            resources.getString(R.string.family_status_text),
            if (actualData.familyStatus != "") actualData.familyStatus else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_family_status_text),
            viewModel.familyStatusValues
        ) {
            currentLifestyleData.familyStatus =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Participation in social and cultural events
        addLifestyleFieldChooseView(
            resources.getString(R.string.event_participation),
            if (actualData.eventsParticipation != "") actualData.eventsParticipation else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_event_participation),
            viewModel.eventParticipationValues
        ) {
            currentLifestyleData.eventsParticipation =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Physical activity
        addLifestyleFieldChooseView(
            resources.getString(R.string.physical_activity),
            if (actualData.physicalActivity != "") actualData.physicalActivity else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_physical_activity),
            viewModel.physicalActivityValues
        ) {
            currentLifestyleData.physicalActivity =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Job status
        addLifestyleFieldChooseView(
            resources.getString(R.string.job_status),
            if (actualData.workStatus != "") actualData.workStatus else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_job_status),
            viewModel.workStatusValues
        ) {
            currentLifestyleData.workStatus =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Significant Value High
        addLifestyleFieldChooseView(
            resources.getString(R.string.significant_value_high),
            if (actualData.significantValueHigh != "") actualData.significantValueHigh else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_significant_value_high),
            viewModel.lifeValues
        ) {
            currentLifestyleData.significantValueHigh =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Significant Value Medium
        addLifestyleFieldChooseView(
            resources.getString(R.string.significant_value_medium),
            if (actualData.significantValueMedium != "") actualData.significantValueMedium else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_significant_value_medium),
            viewModel.lifeValues
        ) {
            currentLifestyleData.significantValueMedium =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Significant Value Low
        addLifestyleFieldChooseView(
            resources.getString(R.string.significant_value_low),
            if (actualData.significantValueLow != "") actualData.significantValueLow else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_significant_value_low),
            viewModel.lifeValues
        ) {
            currentLifestyleData.significantValueLow =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

    }

    private fun addLifestyleFieldChooseView(
        name: String,
        value: String,
        alertDialogTitle: String,
        availableValues: List<String>,
        action: (String) -> Unit,
    ) {
        val view: View =
            layoutInflater.inflate(R.layout.lifestyle_field_choose_view, binding.root, false)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val valueTextViewAlert = view.findViewById<TextView>(R.id.valueTextViewAlert)

        nameTextView.text = name
        valueTextViewAlert.text = value

        valueTextViewAlert.setOnClickListener {
            val builder = AlertDialog.Builder(context)
            builder.setTitle(alertDialogTitle)
            builder.setItems(availableValues.toTypedArray()) { dialog, which ->
                val newValue = availableValues[which]
                if (newValue != valueTextViewAlert.text) {
                    valueTextViewAlert.text = newValue
                    action.invoke(newValue)
                }
            }
            val dialog = builder.create()
            dialog.show()
        }

        binding.lifestyleLinearLayout.addView(view)
    }

}