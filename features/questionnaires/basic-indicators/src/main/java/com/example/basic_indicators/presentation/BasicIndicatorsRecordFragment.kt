package com.example.basic_indicators.presentation

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.basic_indicators.R
import com.example.basic_indicators.databinding.FragmentBasicIndicatorsRecordBinding
import com.example.basic_indicators.domain.entities.GetBasicIndicatorResponseEntity
import com.example.basic_indicators.domain.entities.GetCVERiskRequestEntity
import com.example.common.BackendExceptions
import com.example.common.flows.Error
import com.example.common.flows.Success
import com.example.presentation.BaseFragment
import com.example.presentation.setColor
import com.example.presentation.setTextBySample
import com.example.presentation.smartEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject
import kotlin.math.roundToInt

@AndroidEntryPoint
class BasicIndicatorsRecordFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_basic_indicators_record) {

    val basicIndicatorId by lazy {
        arguments?.getLong(ARG_ID)
    }

    override val viewModel by viewModels<BasicIndicatorsRecordViewModel>()
    private val viewModelBasicIndicator by activityViewModels<BasicIndicatorsViewModel>()

    private val basicIndicator by lazy {
        if (basicIndicatorId != null) {
            viewModelBasicIndicator.getLaboratoryResearchesById(basicIndicatorId!!)
                ?: viewModelBasicIndicator.getDefaultLBasicIndicatorRecord()
        } else {
            viewModelBasicIndicator.getDefaultLBasicIndicatorRecord()
        }
    }
    private lateinit var currentBasicIndicator: GetBasicIndicatorResponseEntity

    private val binding by viewBinding(FragmentBasicIndicatorsRecordBinding::bind)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentBasicIndicator = viewModelBasicIndicator.basicIndicatorsChangedMap[basicIndicatorId]
            ?: basicIndicator.copy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFieldsLogic()

        with(binding) {
            cvEventsRiskValue.setOnClickListener {
                cvEventsRiskDescription.visibility = when (cvEventsRiskDescription.visibility) {
                    View.GONE -> View.VISIBLE
                    else -> View.GONE
                }
            }
            idealCardiovascularAge.setOnClickListener {
                idealCardiovascularAgeDescription.visibility =
                    when (idealCardiovascularAgeDescription.visibility) {
                        View.GONE -> View.VISIBLE
                        else -> View.GONE
                    }
            }
        }

        observeCVERisk()
        observeIdealAge()
    }

    fun resetFields() {
        viewModelBasicIndicator.basicIndicatorsChangedMap.remove(basicIndicatorId)
        currentBasicIndicator = basicIndicator.copy()
        initFields()
    }

    private fun checkDifference() {
        if (currentBasicIndicator != basicIndicator) {
            viewModelBasicIndicator.basicIndicatorsChangedMap[basicIndicatorId] =
                currentBasicIndicator
            viewModelBasicIndicator.currentBasicIndicatorChanged.value = true
        } else {
            viewModelBasicIndicator.basicIndicatorsChangedMap.remove(basicIndicatorId)
            viewModelBasicIndicator.currentBasicIndicatorChanged.value = false
        }
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
                        viewModelBasicIndicator.onOutOfRangeToast(
                            resources.getString(toastErrorParamRes),
                            range
                        )
                    }
                }

            weightTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 40.0..160.0,
                sampleId = R.string.unit_kg,
                onError = onError(R.string.weight, 40.0..160.0)
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
                    bodyMassIndexTextEdit.setColor(currentBasicIndicator.bodyMassIndex, 18.5..24.99)
                    checkDifference()
                }
                currentBasicIndicator.weight
            }
            heightTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 145.0..230.0,
                sampleId = R.string.unit_sm,
                onError = onError(R.string.height, 145.0..230.0)
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
                    bodyMassIndexTextEdit.setColor(currentBasicIndicator.bodyMassIndex, 18.5..24.99)
                    checkDifference()
                }
                currentBasicIndicator.height
            }
            waistTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 50.0..190.0,
                sampleId = R.string.unit_sm,
                onError = onError(R.string.waist, 50.0..190.0)
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
                imm = inputMethodManager,
                range = 80.0..250.0,
                positiveRange = 100.0..130.0,
                sampleId = R.string.unit_mm_rt_st,
                onError = onError(R.string.systolic_blood_pressure_level, 80.0..250.0)
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
                imm = inputMethodManager,
                range = 3.0..15.2,
                positiveRange = 2.8..5.2,
                sampleId = R.string.unit_mmol_by_l,
                onError = onError(R.string.total_cholesterol, 3.0..15.2)
            ) {
                if (it != null && it != currentBasicIndicator.totalCholesterolLevel) {
                    currentBasicIndicator.totalCholesterolLevel = it
                    checkDifference()
                }
                currentBasicIndicator.totalCholesterolLevel
            }

            // buttons
            cvEventsRiskCalculateButton.setOnClickListener {
                viewModel.getCVERisk(
                    GetCVERiskRequestEntity(
                        gender = currentBasicIndicator.gender,
                        smoking = currentBasicIndicator.smoking,
                        sbpLevel = currentBasicIndicator.sbpLevel,
                        totalCholesterolLevel = currentBasicIndicator.totalCholesterolLevel
                    )
                )
            }

            idealCardiovascularAgeCalculateButton.setOnClickListener {
                viewModel.getIdealAge(
                    GetCVERiskRequestEntity(
                        gender = currentBasicIndicator.gender,
                        smoking = currentBasicIndicator.smoking,
                        sbpLevel = currentBasicIndicator.sbpLevel,
                        totalCholesterolLevel = currentBasicIndicator.totalCholesterolLevel
                    )
                )
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
                value = currentBasicIndicator.weight,
                text = resources.getString(R.string.unit_kg),
            )
            heightTextEdit.setTextBySample(
                value = currentBasicIndicator.height,
                text = resources.getString(R.string.unit_sm)
            )
            bodyMassIndexTextEdit.setText(currentBasicIndicator.bodyMassIndex.toString())
            bodyMassIndexTextEdit.setColor(currentBasicIndicator.bodyMassIndex, 18.5..24.99)
            waistTextEdit.setTextBySample(
                value = currentBasicIndicator.waistSize,
                text = resources.getString(R.string.unit_sm)
            )
            genderTextViewAlert.text = currentBasicIndicator.gender
            systolicBloodPressureLevelTextEdit.setTextBySample(
                value = currentBasicIndicator.sbpLevel,
                text = resources.getString(R.string.unit_mm_rt_st),
                positiveRange = 100.0..130.0,
            )
            smokingCheckbox.isChecked = currentBasicIndicator.smoking
            totalCholesterolLevelTextEdit.setTextBySample(
                value = currentBasicIndicator.totalCholesterolLevel,
                text = resources.getString(R.string.unit_mmol_by_l),
                positiveRange = 2.8..5.2,
            )
            cvEventsRiskValueTextEdit.setText(currentBasicIndicator.cvEventsRiskValue.toString() + "%")
            cvEventsRiskValueTextEdit.setTextColor(getColorByOption(currentBasicIndicator.scale))
            idealCardiovascularAgeTextEdit.setText(currentBasicIndicator.idealCardiovascularAgesRange)
            idealCardiovascularAgeTextEdit.setTextColor(getColorByOption(currentBasicIndicator.scale))

        }
    }

    private fun observeCVERisk() {
        viewModel.cveRisk.observe(viewLifecycleOwner) { result ->
            if (result is Success) {
                with(result.value) {
                    currentBasicIndicator.cvEventsRiskValue = this.value
                    binding.cvEventsRiskValueTextEdit.setText(this.value.toString() + "%")
                    binding.cvEventsRiskValueTextEdit.setTextColor(getColorByOption(this.scale))
                    checkDifference()
                }
            } else if (result is Error) {
                (result.error as? BackendExceptions)
                    ?.let { viewModel.uiActions.toast(it.description) }
            }
        }
    }

    private fun observeIdealAge() {
        viewModel.idealAge.observe(viewLifecycleOwner) { result ->
            if (result is Success) {
                with(result.value) {
                    currentBasicIndicator.idealCardiovascularAgesRange = this.value
                    binding.idealCardiovascularAgeTextEdit.setText(this.value)
                    binding.idealCardiovascularAgeTextEdit.setTextColor(getColorByOption(this.scale))
                    checkDifference()
                }
            } else if (result is Error) {
                (result.error as? BackendExceptions)
                    ?.let { viewModel.uiActions.toast(it.description) }
            }
        }
    }

    private fun getColorByOption(color: String) =
        resources.getColor(
            when (color) {
                "positive" -> com.example.theme.R.color.green_color
                "neutral" -> com.example.theme.R.color.yellow_color
                "negative" -> com.example.theme.R.color.active_color
                else -> com.example.theme.R.color.enter_color
            }
        )

    companion object {
        const val ARG_ID = "ID"
    }

}