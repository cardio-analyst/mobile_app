package `is`.ulstu.cardioanalyst.ui.laboratory_research

import `is`.ulstu.cardioanalyst.R
import android.annotation.SuppressLint
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


@SuppressLint("SetTextI18n")
fun <T : Comparable<T>> EditText.setTextBySample(
    value: T,
    text: String? = null,
    positiveRange: ClosedFloatingPointRange<T>? = null,
) {
    setText(if (text != null) "$value $text" else "$value")
    if (positiveRange != null) {
        setColor(value, positiveRange)
    }
}

fun <T : Comparable<T>> EditText.smartEditText(
    imm: InputMethodManager,
    range: ClosedFloatingPointRange<T>,
    positiveRange: ClosedFloatingPointRange<T>? = null,
    sampleId: Int?,
    onError: () -> Unit,
    block: (T?) -> T
) {

    setOnFocusChangeListener { view, isFocused ->
        if (isFocused) {
            setText(" ")
            imm.showSoftInput(view, 0)
        } else {
            val result = block.invoke(null)
            setTextBySample(
                result,
                sampleId?.let { resources.getString(it) } ?: ""
            )
        }
    }

    setOnClickListener {
        setText(" ")
    }

    setOnEditorActionListener { _, actionId, _ ->
        if (actionId == EditorInfo.IME_ACTION_DONE) {
            var value: T? = null
            try {
                val str = text.toString()
                value = when (range.start) {
                    is Int -> str.toInt() as T
                    is Double -> str.toDouble() as T
                    else -> throw Exception()
                }
                if (value !in range) {
                    value = null
                    throw Exception()
                }
            } catch (e: Exception) {
                // uiAction
                onError.invoke()
            }
            val result = block.invoke(value)
            setTextBySample(
                value = result,
                sampleId?.let { resources.getString(it) } ?: "",
                positiveRange
            )
            return@setOnEditorActionListener false
        }
        false
    }
}

fun <T : Comparable<T>> EditText.setColor(
    param: T,
    positiveRange: ClosedFloatingPointRange<T>
) {
    setTextColor(
        resources.getColor(
            if (param in positiveRange) R.color.green_color
            else R.color.enter_color
        )
    )
}