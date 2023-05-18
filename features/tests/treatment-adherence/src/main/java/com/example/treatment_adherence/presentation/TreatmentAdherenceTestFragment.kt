package com.example.treatment_adherence.presentation

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import androidx.core.util.isNotEmpty
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.presentation.BaseFragment
import com.example.treatment_adherence.R
import com.example.treatment_adherence.databinding.FragmentLifestyleTestBinding
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class TreatmentAdherenceTestFragment @Inject constructor() :
    BaseFragment(R.layout.fragment_lifestyle_test) {

    override val viewModel by viewModels<TreatmentAdherenceTestViewModel>()

    private val binding by viewBinding(FragmentLifestyleTestBinding::bind)

    /**
    Название                        | Переменная | Вопросы для оценки | Балл
    Важность лекарственной терапии          | Md | 2, 3, 4, 6, 14     | 0
    Важность медицинского сопровождения     | Mm | 1, 5, 10, 11, 13   | 0
    Важность модификации образа жизни       | Mc | 7, 8, 9, 12, 15    | 0
    Готовность к лекарственной терапии      | Gd | 16, 17, 18, 20, 21 | 0
    Готовность к медицинскому сопровождению | Gm | 16, 19, 20, 24, 25 | 0
    Готовность к модификации образа жизни   | Gc | 19, 22, 23, 24, 25 | 0
     */
    private val scoreTable: List<Param> = listOf(
        Param(listOf(1, 2, 3, 5, 13), 0),
        Param(listOf(0, 4, 9, 10, 12), 0),
        Param(listOf(6, 7, 8, 11, 14), 0),
        Param(listOf(15, 16, 17, 19, 20), 0),
        Param(listOf(15, 18, 19, 23, 24), 0),
        Param(listOf(18, 21, 22, 23, 24), 0),
    )

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.answersListView.choiceMode = ListView.CHOICE_MODE_SINGLE
        binding.testNameTextView.text = resources.getString(R.string.treatment_adherence)
        initQuestion()
    }

    private fun initQuestion() {
        val question = viewModel.getNextQuestion()
        if (question != null) {
            with(binding) {
                questionNameTextView.text = question.questionName
                val adapter =
                    this@TreatmentAdherenceTestFragment.context?.let { context ->
                        ArrayAdapter(
                            context,
                            com.example.presentation.R.layout.simple_list_item_multiple_choice,
                            question.questionsAnswers
                        )
                    }
                answersListView.adapter = adapter

                nextButton.setOnClickListener {
                    val checked = answersListView.checkedItemPositions
                    if (checked.isNotEmpty()) {
                        for (index in 0 until checked.size()) {
                            val answerItemNumber = checked.keyAt(index)
                            // checked or not checked answer
                            val checkedItem = checked[answerItemNumber]
                            if (checkedItem) {
                                scoreTable.forEach {
                                    if (it.questionsNumbers.contains(question.questionNumber))
                                        it.value += answerItemNumber + 1
                                }
                            }
                        }
                        initQuestion()
                    }
                }
            }
        } else {
            // show finish window
            with(binding) {
                questionNameTextView.visibility = View.INVISIBLE
                answersListView.visibility = View.INVISIBLE
                resultTextView.visibility = View.VISIBLE
                resultDescriptionTextView.visibility = View.VISIBLE
                nextButton.text = resources.getString(R.string.button_text_finish)

                // Cd
                val adherenceToDrugTherapy =
                    1 / (((30).toDouble() / scoreTable[0].value) * (60 / scoreTable[3].value) / 2) * 100
                // Cm
                val commitmentToMedicalSupport =
                    1 / (((30).toDouble() / scoreTable[1].value) * (60 / scoreTable[4].value) / 2) * 100
                // Cc
                val commitmentToLifestyleModification =
                    1 / (((30).toDouble() / scoreTable[2].value) * (60 / scoreTable[5].value) / 2) * 100
                // С
                val treatmentAdherence =
                    (commitmentToMedicalSupport + 2 * commitmentToLifestyleModification + 3 * adherenceToDrugTherapy) / 6

                resultDescriptionTextView.text = resources.getString(
                    R.string.treatment_adherence_test_result,
                    getResult(treatmentAdherence),
                    getResult(adherenceToDrugTherapy),
                    getResult(commitmentToMedicalSupport),
                    getResult(commitmentToLifestyleModification)
                )

                nextButton.setOnClickListener {
                    // go back
                    viewModel.finish(
                        Triple(
                            adherenceToDrugTherapy,
                            commitmentToMedicalSupport,
                            commitmentToLifestyleModification
                        )
                    )
                }
            }
        }
    }

    private fun getResult(param: Double): String = when {
        param < 50 -> resources.getString(R.string.treatment_adherence_low)
        param < 75 -> resources.getString(R.string.treatment_adherence_medium)
        else -> resources.getString(R.string.treatment_adherence_high)
    }

    private data class Param(val questionsNumbers: List<Int>, var value: Int)
}