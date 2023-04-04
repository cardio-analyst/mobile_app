package com.example.laboratory_research.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.laboratory_research.R
import com.example.laboratory_research.databinding.FragmentLaboratoryResearchRecordBinding
import com.example.laboratory_research.domain.entities.GetLaboratoryResearchResponseEntity
import com.example.presentation.setTextBySample
import com.example.presentation.smartEditText
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class LaboratoryResearchRecordFragment @Inject constructor() : Fragment(R.layout.fragment_laboratory_research_record) {

    val laboratoryResearchId by lazy {
        arguments?.getLong(ARG_ID)
    }

    private val viewModel by activityViewModels<LaboratoryResearchViewModel>()

    private val laboratoryResearch by lazy {
        if (laboratoryResearchId != null) {
            viewModel.getLaboratoryResearchesById(laboratoryResearchId!!)
                ?: viewModel.getDefaultLaboratoryResearchRecord()
        } else {
            viewModel.getDefaultLaboratoryResearchRecord()
        }
    }
    private lateinit var currentLaboratoryResearch: GetLaboratoryResearchResponseEntity

    private val binding by viewBinding(FragmentLaboratoryResearchRecordBinding::bind)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        currentLaboratoryResearch =
            viewModel.laboratoryResearchChangedMap[laboratoryResearchId] ?: laboratoryResearch.copy()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initFields()
        initFieldsLogic()
    }

    fun resetFields() {
        viewModel.laboratoryResearchChangedMap.remove(laboratoryResearchId)
        currentLaboratoryResearch = laboratoryResearch.copy()
        initFields()
    }

    private fun checkDifference() {
        if (currentLaboratoryResearch != laboratoryResearch) {
            viewModel.laboratoryResearchChangedMap[laboratoryResearchId] = currentLaboratoryResearch
            viewModel.currentLaboratoryResearchChanged.value = true
        }
        else {
            viewModel.laboratoryResearchChangedMap.remove(laboratoryResearchId)
            viewModel.currentLaboratoryResearchChanged.value = false
        }
    }

    private fun initFieldsLogic() {
        with(binding) {
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            val onError: (Int, ClosedFloatingPointRange<Double>) -> () -> Unit =
                { toastErrorParamRes, range ->
                    {
                        viewModel.onOutOfRangeToast(
                            resources.getString(toastErrorParamRes),
                            range
                        )
                    }
                }

            highDensityCholesterolTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 0.5..5.5,
                positiveRange = 0.72..1.94,
                sampleId = R.string.unit_mmol_by_l,
                onError = onError(R.string.high_density_cholesterol, 0.5..5.5)
            ) {
                if (it != null && it != currentLaboratoryResearch.highDensityCholesterol) {
                    currentLaboratoryResearch.highDensityCholesterol = it
                    checkDifference()
                }
                currentLaboratoryResearch.highDensityCholesterol
            }

            lowDensityCholesterolTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 0.5..8.5,
                positiveRange = 0.72..1.94,
                sampleId = R.string.unit_mmol_by_l,
                onError = onError(R.string.low_density_cholesterol, 0.5..8.5)
            ) {
                if (it != null && it != currentLaboratoryResearch.lowDensityCholesterol) {
                    currentLaboratoryResearch.lowDensityCholesterol = it
                    checkDifference()
                }
                currentLaboratoryResearch.lowDensityCholesterol
            }

            triglyceridesTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 0.2..8.5,
                positiveRange = 0.2..1.7,
                sampleId = R.string.unit_mmol_by_l,
                onError = onError(R.string.triglycerides, 0.2..8.5)
            ) {
                if (it != null && it != currentLaboratoryResearch.triglycerides) {
                    currentLaboratoryResearch.triglycerides = it
                    checkDifference()
                }
                currentLaboratoryResearch.triglycerides
            }

            lipoproteinTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 0.0..10.0,
                positiveRange = 0.01..0.3,
                sampleId = R.string.unit_g_by_l,
                onError = onError(R.string.lipoprotein, 0.0..10.0)
            ) {
                if (it != null && it != currentLaboratoryResearch.lipoprotein) {
                    currentLaboratoryResearch.lipoprotein = it
                    checkDifference()
                }
                currentLaboratoryResearch.lipoprotein
            }

            highlySensitiveCReactiveProteinTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 0.1..12.0,
                positiveRange = 0.0..5.0,
                sampleId = R.string.unit_mg_by_l,
                onError = onError(R.string.highly_sensitive_C_reactive_protein, 0.1..12.0)
            ) {
                if (it != null && it != currentLaboratoryResearch.highlySensitiveCReactiveProtein) {
                    currentLaboratoryResearch.highlySensitiveCReactiveProtein = it
                    checkDifference()
                }
                currentLaboratoryResearch.highlySensitiveCReactiveProtein
            }

            atherogenicCoefficientTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 0.1..8.0,
                positiveRange = 2.0..3.0,
                sampleId = null,
                onError = onError(R.string.atherogenic_coefficient, 0.1..8.0)
            ) {
                if (it != null && it != currentLaboratoryResearch.atherogenicityCoefficient) {
                    currentLaboratoryResearch.atherogenicityCoefficient = it
                    checkDifference()
                }
                currentLaboratoryResearch.atherogenicityCoefficient
            }

            creatinineTextEdit.smartEditText(
                imm = inputMethodManager,
                range = 20.0..500.0,
                positiveRange = 44.0..115.0,
                sampleId = R.string.unit_mmol_by_l,
                onError = onError(R.string.creatinine, 20.0..500.0)
            ) {
                if (it != null && it != currentLaboratoryResearch.creatinine) {
                    currentLaboratoryResearch.creatinine = it
                    checkDifference()
                }
                currentLaboratoryResearch.creatinine
            }

            uzdmagResultsCheckbox.setOnCheckedChangeListener { _, b ->
                currentLaboratoryResearch.atheroscleroticPlaquesPresence = b
                checkDifference()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initFields() {
        with(binding) {
            laboratoryResearchTooltip.text =
                if (laboratoryResearch.createdAt != resources.getString(R.string.lab_res_default_tooltip))
                    "Сохранено: ${laboratoryResearch.createdAt}"
                else
                    laboratoryResearch.createdAt

            highDensityCholesterolTextEdit.setTextBySample(
                value = currentLaboratoryResearch.highDensityCholesterol,
                text = resources.getString(R.string.unit_mmol_by_l),
                positiveRange = 0.72..1.94,
            )
            lowDensityCholesterolTextEdit.setTextBySample(
                value = currentLaboratoryResearch.lowDensityCholesterol,
                text = resources.getString(R.string.unit_mmol_by_l),
                positiveRange = 0.72..1.94,
            )
            triglyceridesTextEdit.setTextBySample(
                value = currentLaboratoryResearch.triglycerides,
                text = resources.getString(R.string.unit_mmol_by_l),
                positiveRange = 0.2..1.7,
            )
            lipoproteinTextEdit.setTextBySample(
                value = currentLaboratoryResearch.lipoprotein,
                text = resources.getString(R.string.unit_g_by_l),
                positiveRange = 0.01..0.3,
            )
            highlySensitiveCReactiveProteinTextEdit.setTextBySample(
                value = currentLaboratoryResearch.highlySensitiveCReactiveProtein,
                text = resources.getString(R.string.unit_mg_by_l),
                positiveRange = 0.0..5.0,
            )
            atherogenicCoefficientTextEdit.setTextBySample(
                value = currentLaboratoryResearch.atherogenicityCoefficient,
                text = null,
                positiveRange = 2.0..3.0,
            )
            creatinineTextEdit.setTextBySample(
                value = currentLaboratoryResearch.creatinine,
                text = resources.getString(R.string.unit_mmol_by_l),
                positiveRange = 44.0..115.0,
            )
            uzdmagResultsCheckbox.isChecked = currentLaboratoryResearch.atheroscleroticPlaquesPresence
        }
    }

    companion object {
        const val ARG_ID = "ID"
    }
}