package com.example.feedback.presentation

import android.graphics.PorterDuff
import android.os.Bundle
import android.view.View
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.feedback.R
import com.example.feedback.databinding.FragmentFeedbackBinding
import com.example.presentation.BaseFragment
import com.example.presentation.ResultViewTools
import com.example.presentation.observeResultsComponent
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject


private const val MARK_COUNT = 5

@AndroidEntryPoint
class FeedbackFragment @Inject constructor() : BaseFragment(R.layout.fragment_feedback) {

    override val viewModel by viewModels<FeedbackViewModel>()

    private val binding by viewBinding(FragmentFeedbackBinding::bind)
    private val resultViewTools by lazy {
        ResultViewTools(this, binding.root, binding.resultView)
    }
    private var mark = 0
    private val markedButtons = Array(MARK_COUNT) { false }
    private val markButtons by lazy {
        with(binding) {
            arrayOf(mark1Button, mark2Button, mark3Button, mark4Button, mark5Button)
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            goBackButton.setOnClickListener { viewModel.goBack() }
            resultView.setTryAgainAction {
                viewModel.reloadSendFeedback(
                    mark,
                    feedbackTextEdit.text.toString()
                )
            }
            resultView.setPendingDescription(resources.getString(R.string.pending_action))
            sendFeedbackButton.setOnClickListener {
                viewModel.sendFeedback(mark, feedbackTextEdit.text.toString())
            }
        }

        initMarksLogic()
        observeSendingFeedback()
    }

    private fun observeSendingFeedback() {
        viewModel.sendFeedback.observeResultsComponent(
            resultViewTools,
            onSessionExpired = null,
        ) {
            viewModel.showSuccessToast()
            viewModel.goBack()
        }
    }

    private fun initMarksLogic() {
        markButtons.forEachIndexed { index, markButton ->
            markButton.setOnClickListener {
                mark = if (markedButtons[index] && index + 1 == mark) {
                    // remove all marks
                    doMarkButtons(-1)
                    0
                } else {
                    // set marks = index + 1
                    doMarkButtons(index)
                    index + 1
                }
            }
        }
    }

    private fun doMarkButtons(markedCountIndex: Int) {
        for (index in markedButtons.indices) {
            val (marked, color) = if (index <= markedCountIndex) {
                Pair(
                    true,
                    resources.getColor(com.example.theme.R.color.yellow_color, context?.theme)
                )
            } else {
                Pair(
                    false,
                    resources.getColor(com.example.theme.R.color.inactive_color, context?.theme)
                )
            }
            markedButtons[index] = marked
            markButtons[index].setColorFilter(
                color,
                PorterDuff.Mode.SRC_IN,
            )
        }
    }

}