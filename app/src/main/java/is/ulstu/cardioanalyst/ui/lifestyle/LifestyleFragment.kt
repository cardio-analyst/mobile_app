package `is`.ulstu.cardioanalyst.ui.lifestyle

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.databinding.FragmentLifestyleBinding
import `is`.ulstu.cardioanalyst.databinding.PairActionButtonsBinding
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.cardioanalyst.ui.lifestyle.tests.LifestyleTestListener
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import by.kirich1409.viewbindingdelegate.viewBinding

class LifestyleFragment : BaseFragment(R.layout.fragment_lifestyle), LifestyleTestListener {

    // no arguments for this screen
    class Screen : BaseScreen

    override val viewModel by screenViewModel<LifestyleViewModel>()

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
            resultView.setTryAgainAction { viewModel.reloadLifestyle() }
        }

        observeLifestyle()
        observeLifestyleSave()
    }

    private fun observeLifestyle() {
        viewModel.lifestyle.observeResults(this, binding.root, binding.resultView, { data ->
            // init views
            currentLifestyleData = data.copy()
            resetViews(currentLifestyleData, data)

            with(actionButtonsBinding) {
                negativeButton.setOnClickListener {
                    resetViews(currentLifestyleData, data)
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
                resultView.setTryAgainAction { viewModel.reloadLifestyle() }
            }
        })
    }

    private fun resetViews(currentLifestyleData: LifestyleMainEntity, data: LifestyleMainEntity) {

        // clear views
        binding.lifestyleLinearLayout.removeAllViews()
        buttonVisibility(View.INVISIBLE)

        // Family status
        addLifestyleFieldChooseView(
            resources.getString(R.string.family_status_text),
            if (data.familyStatus != "") data.familyStatus else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_family_status_text),
            familyStatusValues
        ) {
            currentLifestyleData.familyStatus =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Participation in social and cultural events
        addLifestyleFieldChooseView(
            resources.getString(R.string.event_participation),
            if (data.eventsParticipation != "") data.eventsParticipation else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_event_participation),
            eventParticipationValues
        ) {
            currentLifestyleData.eventsParticipation =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Physical activity
        addLifestyleFieldChooseView(
            resources.getString(R.string.physical_activity),
            if (data.physicalActivity != "") data.physicalActivity else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_physical_activity),
            physicalActivityValues
        ) {
            currentLifestyleData.physicalActivity =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Job status
        addLifestyleFieldChooseView(
            resources.getString(R.string.job_status),
            if (data.workStatus != "") data.workStatus else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_job_status),
            workStatusValues
        ) {
            currentLifestyleData.workStatus =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Significant Value High
        addLifestyleFieldChooseView(
            resources.getString(R.string.significant_value_high),
            if (data.significantValueHigh != "") data.significantValueHigh else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_significant_value_high),
            lifeValues
        ) {
            currentLifestyleData.significantValueHigh =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Significant Value Medium
        addLifestyleFieldChooseView(
            resources.getString(R.string.significant_value_medium),
            if (data.significantValueMedium != "") data.significantValueMedium else resources.getString(
                R.string.default_option_not_choosing
            ),
            resources.getString(R.string.choose_significant_value_medium),
            lifeValues
        ) {
            currentLifestyleData.significantValueMedium =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Significant Value Low
        addLifestyleFieldChooseView(
            resources.getString(R.string.significant_value_low),
            if (data.significantValueLow != "") data.significantValueLow else resources.getString(R.string.default_option_not_choosing),
            resources.getString(R.string.choose_significant_value_low),
            lifeValues
        ) {
            currentLifestyleData.significantValueLow =
                if (it == resources.getString(R.string.default_option_not_choosing)) "" else it
            buttonVisibility(if (currentLifestyleData != data) View.VISIBLE else View.INVISIBLE)
        }

        // Stenocardia Symptoms
        addLifestyleTestView(
            resources.getString(R.string.angina_symptoms),
            when {
                data.anginaScore >= 2 -> resources.getString(R.string.angina_symptoms_symptoms)
                data.anginaScore < 0 -> resources.getString(R.string.default_option_not_passed)
                else -> resources.getString(R.string.angina_symptoms_no_symptoms)
            }
        ) {
            viewModel.startStenocardiaSymptomsTest(this)
        }

        // Treatment Adherence
        val treatmentAdherence =
            (data.adherenceDrugTherapy + 2 * data.adherenceMedicalSupport + 3 * data.adherenceLifestyleMod) / 6
        addLifestyleTestView(
            resources.getString(R.string.treatment_adherence),
            when {
                (data.adherenceDrugTherapy < 0 || data.adherenceMedicalSupport < 0 || data.adherenceLifestyleMod < 0)
                -> resources.getString(R.string.default_option_not_passed)
                treatmentAdherence < 50 -> resources.getString(R.string.treatment_adherence_low)
                treatmentAdherence < 75 -> resources.getString(R.string.treatment_adherence_medium)
                else -> resources.getString(R.string.treatment_adherence_high)
            }
        ) {
            viewModel.startTreatmentAdherenceTest(this)
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

    override fun returnStenocardiaSymptomsResult(score: Int, action: () -> Unit) {
        currentLifestyleData.anginaScore = score
        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_lifestyle_save))
            resultView.setTryAgainAction {
                viewModel.reloadLifestyleSave(
                    currentLifestyleData
                )
            }
        }
        viewModel.setUserLifestyle(currentLifestyleData)

        viewModel.lifestyleSave.observeResults(this, binding.root, binding.resultView, {
            // init views
            action.invoke()
        })
    }

    override fun returnTreatmentAdherenceResult(
        results: Triple<Double, Double, Double>,
        action: () -> Unit
    ) {
        currentLifestyleData.adherenceDrugTherapy = results.first
        currentLifestyleData.adherenceMedicalSupport = results.second
        currentLifestyleData.adherenceLifestyleMod = results.third
        with(binding) {
            resultView.setPendingDescription(resources.getString(R.string.flow_pending_user_lifestyle_save))
            resultView.setTryAgainAction {
                viewModel.reloadLifestyleSave(
                    currentLifestyleData
                )
            }
        }
        viewModel.setUserLifestyle(currentLifestyleData)

        viewModel.lifestyleSave.observeResults(this, binding.root, binding.resultView, {
            // init views
            action.invoke()
        })
    }

    companion object {
        private val familyStatusValues = listOf(
            Singletons.getString(R.string.default_option_not_choosing),
            Singletons.getString(R.string.family_status_married),
            Singletons.getString(R.string.family_status_not_married),
            Singletons.getString(R.string.family_status_divorced),
            Singletons.getString(R.string.family_status_widower),
        )

        private val eventParticipationValues = listOf(
            Singletons.getString(R.string.default_option_not_choosing),
            Singletons.getString(R.string.event_participation_one_in_week),
            Singletons.getString(R.string.event_participation_more_than_one_in_week)
        )

        private val physicalActivityValues = listOf(
            Singletons.getString(R.string.physical_activity_one_in_week),
            Singletons.getString(R.string.physical_activity_more_than_one_in_week),
            Singletons.getString(R.string.physical_activity_one_in_day),
        )

        private val workStatusValues = listOf(
            Singletons.getString(R.string.work_status_worker),
            Singletons.getString(R.string.work_status_unemployed),
            Singletons.getString(R.string.work_status_looking_for_job),
            Singletons.getString(R.string.work_status_pensioner),
        )

        private val lifeValues = listOf(
            Singletons.getString(R.string.life_values_active_life),
            Singletons.getString(R.string.life_values_life_wisdom),
            Singletons.getString(R.string.life_values_health),
            Singletons.getString(R.string.life_values_interesting_job),
            Singletons.getString(R.string.life_values_beauty_of_nature_and_art),
            Singletons.getString(R.string.life_values_love),
            Singletons.getString(R.string.life_values_financially_secure_life),
            Singletons.getString(R.string.life_values_having_good_and_true_friends),
            Singletons.getString(R.string.life_values_public_vocation),
            Singletons.getString(R.string.life_values_knowledge_and_intellectual_development),
            Singletons.getString(R.string.life_values_productive_life),
            Singletons.getString(R.string.life_values_entertainment),
            Singletons.getString(R.string.life_values_autonomy),
            Singletons.getString(R.string.life_values_happy_family_life),
            Singletons.getString(R.string.life_values_creativity),
            Singletons.getString(R.string.life_values_self_confidence),
        )
    }
}