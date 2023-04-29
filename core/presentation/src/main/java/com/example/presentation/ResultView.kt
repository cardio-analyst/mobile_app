package com.example.presentation

import com.example.common.AppException
import com.example.common.BackendExceptions
import com.example.common.ConnectionException
import com.example.presentation.uiactions.UiAction
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import com.example.common.flows.Error
import com.example.common.flows.Pending
import com.example.common.flows.ResultState
import com.example.presentation.databinding.PartResultViewBinding

/**
 * Display progress-bar for [Pending] result, error message and try again button
 * for [Error] result and nothing else for [Empty] and [Success] results
 */
class ResultView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ConstraintLayout(context, attrs, defStyleAttr) {

    private val binding: PartResultViewBinding
    private var tryAgainAction: (() -> Unit)? = null
    private var pendingDescription: String? = null

    init {
        val inflater = LayoutInflater.from(context)
        inflater.inflate(R.layout.part_result_view, this, true)
        binding = PartResultViewBinding.bind(this)
    }

    fun setPendingDescription(pendingDescription: String) {
        this.pendingDescription = pendingDescription
    }

    /**
     * Assign an action for 'Try Again' button.
     */
    fun setTryAgainAction(action: () -> Unit) {
        this.tryAgainAction = action
    }

    /**
     * Set the current result to be displayed to the user.
     */
    fun <T> setResult(fragment: BaseFragment, resultState: ResultState<T>) {
        binding.messageTextView.isVisible =
            resultState is Error<*> || (resultState is Pending<*> && pendingDescription != null)
        binding.errorButton.isVisible = resultState is Error<*>
        binding.progressBar.isVisible = resultState is Pending<*>
        if (resultState is Error) {
            val message = when (resultState.error) {
                is ConnectionException -> resultState.error.message
                is BackendExceptions -> (resultState.error as BackendExceptions).description
                is AppException -> resultState.error.message
                else -> context.getString(R.string.unknown_exception)
            }
            binding.messageTextView.text = message
            renderTryAgainButton()
        }
        if (resultState is Pending && pendingDescription != null) {
            binding.messageTextView.text = pendingDescription
        }
    }

    /**
     * Set the current result to be displayed to the user ignoring Error
     */
    fun <T> setResult(fragment: BaseFragment, resultState: ResultState<T>, uiAction: UiAction) {
        binding.messageTextView.isVisible = resultState is Pending<*> && pendingDescription != null
        binding.errorButton.isVisible = false
        binding.progressBar.isVisible = resultState is Pending<*>
        if (resultState is Error) {
            val message = when (resultState.error) {
                is ConnectionException -> resultState.error.message
                is BackendExceptions -> (resultState.error as BackendExceptions).description
                is AppException -> resultState.error.message
                else -> context.getString(R.string.unknown_exception)
            }
            message?.let { uiAction.toast(it) }
        }
        if (resultState is Pending && pendingDescription != null) {
            binding.messageTextView.text = pendingDescription
        }
    }

    private fun renderTryAgainButton() {
        binding.errorButton.setOnClickListener { tryAgainAction?.invoke() }
        binding.errorButton.setText(R.string.action_try_again)
    }

}