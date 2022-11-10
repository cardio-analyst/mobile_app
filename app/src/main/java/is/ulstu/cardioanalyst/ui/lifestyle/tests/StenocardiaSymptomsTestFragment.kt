package `is`.ulstu.cardioanalyst.ui.lifestyle.tests

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.databinding.FragmentLifestyleTestBinding
import `is`.ulstu.foundation.views.BaseFragment
import `is`.ulstu.foundation.views.BaseScreen
import `is`.ulstu.foundation.views.screenViewModel
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import androidx.core.util.isNotEmpty
import by.kirich1409.viewbindingdelegate.viewBinding

class StenocardiaSymptomsTestFragment() :
    BaseFragment(R.layout.fragment_lifestyle_test) {

    // no arguments for this screen
    class Screen(
        val lifestyleTestListener: LifestyleTestListener
    ) : BaseScreen

    override val viewModel by screenViewModel<StenocardiaSymptomsTestViewModel>()

    private val binding by viewBinding(FragmentLifestyleTestBinding::bind)

    private var score: Int = 0

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.testNameTextView.text = resources.getString(R.string.angina_symptoms)
        initQuestion()
    }

    private fun initQuestion() {
        val question = viewModel.getNextQuestion()
        if (question != null) {
            with(binding) {
                questionNameTextView.text = question.questionName
                val adapter =
                    this@StenocardiaSymptomsTestFragment.context?.let { context ->
                        ArrayAdapter(
                            context,
                            R.layout.simple_list_item_multiple_choice,
                            question.questionsAnswers.map { it.first }.toList()
                        )
                    }
                answersListView.clearChoices()
                answersListView.adapter = adapter
                binding.answersListView.choiceMode = question.choiceMode

                nextButton.setOnClickListener {
                    val checked = answersListView.checkedItemPositions
                    if (checked.isNotEmpty()) {
                        for (index in 0 until checked.size()) {
                            val answerItemNumber = checked.keyAt(index)
                            // checked or not checked answer
                            val checkedItem = checked[answerItemNumber]
                            if (checkedItem) {
                                // valued or not valued answer
                                val itemScored = question.questionsAnswers[answerItemNumber].second
                                if (itemScored) {
                                    score++
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

                val result = if (score >= 2) {
                    resources.getString(R.string.angina_symptoms_symptoms)
                } else {
                    resources.getString(R.string.angina_symptoms_no_symptoms)
                }
                resultDescriptionTextView.text = result

                nextButton.setOnClickListener {
                    // go back
                    viewModel.finish(score)
                }
            }
        }
    }
}