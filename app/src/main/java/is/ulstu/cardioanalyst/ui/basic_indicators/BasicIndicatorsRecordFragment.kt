package `is`.ulstu.cardioanalyst.ui.basic_indicators

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.BackendException
import `is`.ulstu.cardioanalyst.app.BackendExceptions
import `is`.ulstu.cardioanalyst.databinding.FragmentBasicIndicatorsRecordBinding
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.GetBasicIndicatorResponseEntity
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.entities.GetCVERiskRequestEntity
import `is`.ulstu.cardioanalyst.ui.laboratory_research.setTextBySample
import `is`.ulstu.cardioanalyst.ui.laboratory_research.smartEditText
import `is`.ulstu.foundation.model.Error
import `is`.ulstu.foundation.model.Success
import `is`.ulstu.foundation.model.observeResults
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import kotlin.math.roundToInt

class BasicIndicatorsRecordFragment(
    private val basicIndicator: GetBasicIndicatorResponseEntity,
    private val basicIndicatorRecordListener: BasicIndicatorRecordListener
) : BaseFragment(R.layout.fragment_basic_indicators_record) {

    // no arguments for this screen
    class Screen : BaseScreen

    var currentBasicIndicator: GetBasicIndicatorResponseEntity = basicIndicator.copy()

    override val viewModel by screenViewModel<BasicIndicatorsRecordViewModel>()

    private val binding by viewBinding(FragmentBasicIndicatorsRecordBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFieldsLogic()

        observeCVERisk()
        observeIdealAge()
    }

    fun resetFields() {
        currentBasicIndicator = basicIndicator.copy()
        initFields()
    }

    fun checkDifference() {
        return if (currentBasicIndicator != basicIndicator)
            basicIndicatorRecordListener.sengMessageChanges()
        else
            basicIndicatorRecordListener.sengMessageChanges(isChanged = false)
    }

    private fun calculateBMI(weight: Double, height: Double): Double =
        (weight / (height * height) * 100).roundToInt().toDouble() / 100

    private fun initFieldsLogic() {
        with(binding) {
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val onError: (Int, ClosedFloatingPointRange<Double>) -> () -> Unit =
                { toastErrorParamRes, range ->
                    {
                        basicIndicatorRecordListener.makeToast(
                            resources.getString(toastErrorParamRes),
                            range
                        )
                    }
                }

            weightTextEdit.smartEditText(
                inputMethodManager,
                40.0..160.0,
                R.string.unit_kg,
                onError(R.string.weight, 40.0..160.0)
            ) {
                if (it != null && it != currentBasicIndicator.weight) {
                    currentBasicIndicator.weight = it
                    currentBasicIndicator.bodyMassIndex = calculateBMI(
                        currentBasicIndicator.weight,
                        currentBasicIndicator.height / 100
                    )
                    bodyMassIndexTextEdit.setText(
                        currentBasicIndicator.bodyMassIndex.toString()
                    )
                    checkDifference()
                }
                currentBasicIndicator.weight
            }
            heightTextEdit.smartEditText(
                inputMethodManager,
                145.0..230.0,
                R.string.unit_sm,
                onError(R.string.height, 145.0..230.0)
            ) {
                if (it != null && it != currentBasicIndicator.height) {
                    currentBasicIndicator.height = it
                    currentBasicIndicator.bodyMassIndex = calculateBMI(
                        currentBasicIndicator.weight,
                        currentBasicIndicator.height / 100
                    )
                    bodyMassIndexTextEdit.setText(
                        currentBasicIndicator.bodyMassIndex.toString()
                    )
                    checkDifference()
                }
                currentBasicIndicator.height
            }
            waistTextEdit.smartEditText(
                inputMethodManager,
                50.0..190.0,
                R.string.unit_sm,
                onError(R.string.waist, 50.0..190.0)
            ) {
                if (it != null && it != currentBasicIndicator.waistSize) {
                    currentBasicIndicator.waistSize = it
                    checkDifference()
                }
                currentBasicIndicator.waistSize
            }
            genderTextViewAlert.setOnClickListener {
                val builder = AlertDialog.Builder(context)
                builder.setTitle(resources.getString(R.string.choose_gender))
                val availableValues = arrayOf(
                    resources.getString(R.string.genderMan),
                    resources.getString(R.string.genderWoman)
                )
                builder.setItems(availableValues) { dialog, which ->
                    val newValue = availableValues[which]
                    if (newValue != genderTextViewAlert.text) {
                        genderTextViewAlert.text = newValue
                        currentBasicIndicator.gender = newValue
                        checkDifference()
                    }
                }
                val dialog = builder.create()
                dialog.show()
            }
            systolicBloodPressureLevelTextEdit.smartEditText(
                inputMethodManager,
                80.0..250.0,
                R.string.unit_mm_rt_st,
                onError(R.string.systolic_blood_pressure_level, 3.0..15.2)
            ) {
                if (it != null && it != currentBasicIndicator.sbpLevel) {
                    currentBasicIndicator.sbpLevel = it
                    checkDifference()
                }
                currentBasicIndicator.sbpLevel
            }
            smokingCheckbox.setOnCheckedChangeListener { _, b ->
                currentBasicIndicator.smoking = b
                checkDifference()
            }
            totalCholesterolLevelTextEdit.smartEditText(
                inputMethodManager,
                3.0..15.2,
                R.string.unit_mm_rt_st,
                onError(R.string.total_cholesterol, 3.0..15.2)
            ) {
                if (it != null && it != currentBasicIndicator.totalCholesterolLevel) {
                    currentBasicIndicator.totalCholesterolLevel = it
                    checkDifference()
                }
                currentBasicIndicator.totalCholesterolLevel
            }

            // buttons
            cvEventsRiskCalculateButton.setOnClickListener {
                viewModel.getCVERisk(GetCVERiskRequestEntity(
                    gender = currentBasicIndicator.gender,
                    smoking = currentBasicIndicator.smoking,
                    sbpLevel = currentBasicIndicator.sbpLevel,
                    totalCholesterolLevel = currentBasicIndicator.totalCholesterolLevel
                ))
            }

            idealCardiovascularAgeCalculateButton.setOnClickListener {
                viewModel.getIdealAge(GetCVERiskRequestEntity(
                    gender = currentBasicIndicator.gender,
                    smoking = currentBasicIndicator.smoking,
                    sbpLevel = currentBasicIndicator.sbpLevel,
                    totalCholesterolLevel = currentBasicIndicator.totalCholesterolLevel
                ))
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initFields() {
        with(binding) {
            basicIndicatorsTooltip.text =
                if (basicIndicator.createdAt != resources.getString(R.string.basic_indicators_default_tooltip))
                    "Сохранено: ${basicIndicator.createdAt}"
                else
                    basicIndicator.createdAt

            weightTextEdit.setTextBySample(
                basicIndicator.weight.toString(),
                resources.getString(R.string.unit_kg)
            )
            heightTextEdit.setTextBySample(
                basicIndicator.height.toString(),
                resources.getString(R.string.unit_sm)
            )
            bodyMassIndexTextEdit.setText(basicIndicator.bodyMassIndex.toString())
            waistTextEdit.setTextBySample(
                basicIndicator.waistSize.toString(),
                resources.getString(R.string.unit_sm)
            )
            genderTextViewAlert.text = basicIndicator.gender
            systolicBloodPressureLevelTextEdit.setTextBySample(
                basicIndicator.sbpLevel.toString(),
                resources.getString(R.string.unit_mm_rt_st)
            )
            smokingCheckbox.isChecked = basicIndicator.smoking
            totalCholesterolLevelTextEdit.setTextBySample(
                basicIndicator.totalCholesterolLevel.toString(),
                resources.getString(R.string.unit_mmol_by_l)
            )
            cvEventsRiskValueTextEdit.setText(basicIndicator.cvEventsRiskValue.toString())
            idealCardiovascularAgeTextEdit.setText(basicIndicator.idealCardiovascularAgesRange)
        }
    }

    private fun observeCVERisk() {
        viewModel.cveRisk.observe(viewLifecycleOwner) { result ->
            if (result is Success) {
                with(result.value) {
                    currentBasicIndicator.cvEventsRiskValue = this.value
                    binding.cvEventsRiskValueTextEdit.setText(this.value.toString())
                    checkDifference()
                }
            } else if (result is Error && result.error is BackendExceptions) {
                viewModel.uiActions.toast(result.error.description)
            }
        }
    }

    private fun observeIdealAge() {
        viewModel.idealAge.observe(viewLifecycleOwner) { result ->
            if (result is Success) {
                with(result.value) {
                    currentBasicIndicator.idealCardiovascularAgesRange = this.value
                    binding.idealCardiovascularAgeTextEdit.setText(this.value)
                    checkDifference()
                }
            } else if (result is Error && result.error is BackendExceptions) {
                viewModel.uiActions.toast(result.error.description)
            }
        }
    }

    interface BasicIndicatorRecordListener {
        fun <T : Comparable<T>> makeToast(name: String, range: ClosedFloatingPointRange<T>)

        fun sengMessageChanges(isChanged: Boolean = true)
    }

}