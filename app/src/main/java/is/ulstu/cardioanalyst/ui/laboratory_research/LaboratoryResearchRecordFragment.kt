package `is`.ulstu.cardioanalyst.ui.laboratory_research

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentLaboratoryResearchRecordBinding
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.GetLaboratoryResearchResponseEntity
import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.inputmethod.InputMethodManager
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding


class LaboratoryResearchRecordFragment(
    private val laboratoryResearch: GetLaboratoryResearchResponseEntity,
    private val laboratoryResearchRecordListener: LaboratoryResearchRecordListener
) : Fragment(R.layout.fragment_laboratory_research_record) {

    var currentLaboratoryResearch: GetLaboratoryResearchResponseEntity =
        laboratoryResearch.copy()

    private val binding by viewBinding(FragmentLaboratoryResearchRecordBinding::bind)


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initFields()
        initFieldsLogic()
    }

    fun resetFields() {
        currentLaboratoryResearch = laboratoryResearch.copy()
        initFields()
    }

    fun checkDifference() {
        return if (currentLaboratoryResearch != laboratoryResearch)
            laboratoryResearchRecordListener.sengMessageChanges()
        else
            laboratoryResearchRecordListener.sengMessageChanges(isChanged = false)
    }

    private fun initFieldsLogic() {
        with(binding) {
            val inputMethodManager =
                activity?.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

            highDensityCholesterolTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                0.5..5.5,
                R.string.high_density_cholesterol,
                R.string.unit_mmol_by_l
            ) {
                if (it != null && it != currentLaboratoryResearch.highDensityCholesterol) {
                    currentLaboratoryResearch.highDensityCholesterol = it
                    checkDifference()
                }
                currentLaboratoryResearch.highDensityCholesterol
            }

            lowDensityCholesterolTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                0.5..8.5,
                R.string.low_density_cholesterol,
                R.string.unit_mmol_by_l
            ) {
                if (it != null && it != currentLaboratoryResearch.lowDensityCholesterol) {
                    currentLaboratoryResearch.lowDensityCholesterol = it
                    checkDifference()
                }
                currentLaboratoryResearch.lowDensityCholesterol
            }

            triglyceridesTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                0.2..8.5,
                R.string.triglycerides,
                R.string.unit_mmol_by_l
            ) {
                if (it != null && it != currentLaboratoryResearch.triglycerides) {
                    currentLaboratoryResearch.triglycerides = it
                    checkDifference()
                }
                currentLaboratoryResearch.triglycerides
            }

            lipoproteinTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                0.0..10.0,
                R.string.lipoprotein,
                R.string.unit_g_by_l
            ) {
                if (it != null && it != currentLaboratoryResearch.lipoprotein) {
                    currentLaboratoryResearch.lipoprotein = it
                    checkDifference()
                }
                currentLaboratoryResearch.lipoprotein
            }

            highlySensitiveCReactiveProteinTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                0.1..12.0,
                R.string.highly_sensitive_C_reactive_protein,
                R.string.unit_mg_by_l
            ) {
                if (it != null && it != currentLaboratoryResearch.highlySensitiveCReactiveProtein) {
                    currentLaboratoryResearch.highlySensitiveCReactiveProtein = it
                    checkDifference()
                }
                currentLaboratoryResearch.highlySensitiveCReactiveProtein
            }

            atherogenicCoefficientTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                0.1..8.0,
                R.string.atherogenic_coefficient,
                null
            ) {
                if (it != null && it != currentLaboratoryResearch.atherogenicityCoefficient) {
                    currentLaboratoryResearch.atherogenicityCoefficient = it
                    checkDifference()
                }
                currentLaboratoryResearch.atherogenicityCoefficient
            }

            creatinineTextEdit.smartEditText(
                inputMethodManager,
                laboratoryResearchRecordListener,
                20.0..500.0,
                R.string.creatinine,
                R.string.unit_mmol_by_l
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
                laboratoryResearch.highDensityCholesterol.toString(),
                resources.getString(R.string.unit_mmol_by_l)
            )
            lowDensityCholesterolTextEdit.setTextBySample(
                laboratoryResearch.lowDensityCholesterol.toString(),
                resources.getString(R.string.unit_mmol_by_l)
            )
            triglyceridesTextEdit.setTextBySample(
                laboratoryResearch.triglycerides.toString(),
                resources.getString(R.string.unit_mmol_by_l)
            )
            lipoproteinTextEdit.setTextBySample(
                laboratoryResearch.lipoprotein.toString(),
                resources.getString(R.string.unit_g_by_l)
            )
            highlySensitiveCReactiveProteinTextEdit.setTextBySample(
                laboratoryResearch.highlySensitiveCReactiveProtein.toString(),
                resources.getString(R.string.unit_mg_by_l)
            )
            atherogenicCoefficientTextEdit.setText(
                laboratoryResearch.atherogenicityCoefficient.toString()
            )
            creatinineTextEdit.setTextBySample(
                laboratoryResearch.creatinine.toString(),
                resources.getString(R.string.unit_mmol_by_l)
            )
            uzdmagResultsCheckbox.isChecked = laboratoryResearch.atheroscleroticPlaquesPresence
        }
    }

    interface LaboratoryResearchRecordListener {
        fun makeToast(name: String, range: ClosedFloatingPointRange<Double>)

        fun sengMessageChanges(isChanged: Boolean = true)
    }
}