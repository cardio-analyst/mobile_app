package `is`.ulstu.cardioanalyst.ui.lifestyle

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentLifestyleBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LifestyleFragment @Inject constructor() : BaseFragment(R.layout.fragment_lifestyle) {

    override val viewModel by viewModels<LifestyleViewModel>()

    private val binding by viewBinding(FragmentLifestyleBinding::bind)
    private val actionButtonsBinding by viewBinding(PairActionButtonsBinding::bind)
    private lateinit var buttonVisibility: (visibility: Int) -> Unit

    private lateinit var currentLifestyleData: LifestyleMainEntity

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(actionButtonsBinding) {
            buttonVisibility = {
                negativeButton.visibility = it
                positiveButton.visibility = it
            }
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
        viewModel.lifestyle.observeResults(this, binding.root, binding.resultView, { data ->
            // init views
            currentLifestyleData = viewModel.getCurrentLifestyleData() ?: data.copy()
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
        })
    }

    private fun observeLifestyleSave() {
        viewModel.lifestyleSave.observeResults(this, binding.root, binding.resultView, {
            viewModel.onSuccessSaveToast()
            with(binding) {
                resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_lifestyle_load))
                resultView.setTryAgainAction { viewModel.getOrReloadLifestyle() }
            }
        })
    }

    private fun resetViews(currentLifestyleData: LifestyleMainEntity, data: LifestyleMainEntity) {

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

        // Stenocardia Symptoms
        addLifestyleTestView(
            resources.getString(R.string.angina_symptoms),
            when {
                actualData.anginaScore >= 2 -> resources.getString(R.string.angina_symptoms_symptoms)
                actualData.anginaScore < 0 -> resources.getString(R.string.default_option_not_passed)
                else -> resources.getString(R.string.angina_symptoms_no_symptoms)
            }
        ) {
            viewModel.setCurrentLifestyleData(this.currentLifestyleData)
            //viewModel.startStenocardiaSymptomsTest()
        }

        // Treatment Adherence
        val treatmentAdherence =
            (actualData.adherenceDrugTherapy + 2 * actualData.adherenceMedicalSupport + 3 * actualData.adherenceLifestyleMod) / 6
        addLifestyleTestView(
            resources.getString(R.string.treatment_adherence),
            when {
                (actualData.adherenceDrugTherapy < 0 || actualData.adherenceMedicalSupport < 0 || actualData.adherenceLifestyleMod < 0)
                -> resources.getString(R.string.default_option_not_passed)
                treatmentAdherence < 50 -> resources.getString(R.string.treatment_adherence_low)
                treatmentAdherence < 75 -> resources.getString(R.string.treatment_adherence_medium)
                else -> resources.getString(R.string.treatment_adherence_high)
            }
        ) {
            viewModel.setCurrentLifestyleData(this.currentLifestyleData)
            //viewModel.startTreatmentAdherenceTest()
        }

    }

    private fun addLifestyleTestView(
        name: String,
        value: String,
        action: () -> Unit,
    ) {
        val view: View =
            layoutInflater.inflate(R.layout.lifestyle_test_result_view, binding.root, false)
        val nameTextView = view.findViewById<TextView>(R.id.nameTextView)
        val resultTextView = view.findViewById<TextView>(R.id.resultTextView)
        val valueTextView = view.findViewById<TextView>(R.id.valueTextView)
        val startTestButton = view.findViewById<Button>(R.id.startTestButton)

        nameTextView.text = name
        valueTextView.text = value

        valueTextView.setTextColor(
            resources.getColor(
                when (value) {
                    resources.getString(R.string.treatment_adherence_high),
                    resources.getString(R.string.angina_symptoms_no_symptoms) -> R.color.green_color
                    resources.getString(R.string.treatment_adherence_medium) -> R.color.yellow_color
                    resources.getString(R.string.treatment_adherence_low),
                    resources.getString(R.string.angina_symptoms_symptoms) -> R.color.active_color
                    else -> R.color.inactive_color
                }
            )
        )

        if (value == resources.getString(R.string.default_option_not_passed))
            resultTextView.setTextColor(resources.getColor(R.color.active_color))

        startTestButton.setOnClickListener {
            action.invoke()
        }

        binding.lifestyleLinearLayout.addView(view)
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