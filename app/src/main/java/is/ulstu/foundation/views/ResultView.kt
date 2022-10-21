package `is`.ulstu.foundation.views

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.BackendExceptions
import `is`.ulstu.cardioanalyst.app.ConnectionException
import `is`.ulstu.cardioanalyst.databinding.PartResultViewBinding
import `is`.ulstu.foundation.model.*
import `is`.ulstu.foundation.uiactions.UiActions
import android.content.ClipDescription
import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible

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
    fun <T> setResult(fragment: BaseFragment, result: Result<T>) {
        binding.messageTextView.isVisible =
            result is Error<*> || (result is Pending<*> && pendingDescription != null)
        binding.errorButton.isVisible = result is Error<*>
        binding.progressBar.isVisible = result is Pending<*>
        if (result is Error) {
            val message = when (result.error) {
                is ConnectionException -> result.error.message
                is BackendExceptions -> result.error.description
                else -> context.getString(R.string.unknown_exception)
            }
            binding.messageTextView.text = message
            renderTryAgainButton()
        }
        if (result is Pending && pendingDescription != null) {
            binding.messageTextView.text = pendingDescription
        }
    }

    /**
     * Set the current result to be displayed to the user ignoring Error
     */
    fun <T> setResult(fragment: BaseFragment, result: Result<T>, uiActions: UiActions) {
        binding.messageTextView.isVisible = result is Pending<*> && pendingDescription != null
        binding.errorButton.isVisible = false
        binding.progressBar.isVisible = result is Pending<*>
        if (result is Error) {
            val message = when (result.error) {
                is ConnectionException -> result.error.message
                is BackendExceptions -> result.error.description
                else -> context.getString(R.string.unknown_exception)
            }
            message?.let { uiActions.toast(it) }
        }
        if (result is Pending && pendingDescription != null) {
            binding.messageTextView.text = pendingDescription
        }
    }

    private fun renderTryAgainButton() {
        binding.errorButton.setOnClickListener { tryAgainAction?.invoke() }
        binding.errorButton.setText(R.string.action_try_again)
    }

}