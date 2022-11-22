package `is`.ulstu.cardioanalyst.ui.laboratory_research

import android.annotation.SuppressLint
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import kotlin.reflect.typeOf


@SuppressLint("SetTextI18n")
fun <T> EditText.setTextBySample(value: T, text: String) {
    setText("${value.toString()} $text")
}

fun <T : Comparable<T>> EditText.smartEditText(
    imm: InputMethodManager,
    range: ClosedFloatingPointRange<T>,
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
                value = when(range.start) {
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
                result,
                sampleId?.let { resources.getString(it) } ?: ""
            )
            return@setOnEditorActionListener false
        }
        false
    }
}