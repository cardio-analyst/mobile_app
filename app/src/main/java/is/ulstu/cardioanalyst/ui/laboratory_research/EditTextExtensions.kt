package `is`.ulstu.cardioanalyst.ui.laboratory_research

import android.annotation.SuppressLint
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.EditText


@SuppressLint("SetTextI18n")
fun <T> EditText.setTextBySample(value: T, text: String) {
    setText("${value.toString()} $text")
}

fun EditText.smartEditText(
    imm: InputMethodManager,
    laboratoryResearchRecordListener: LaboratoryResearchRecordFragment.LaboratoryResearchRecordListener,
    range: ClosedFloatingPointRange<Double>,
    toastErrorParamRes: Int,
    sampleId: Int?,
    block: (Double?) -> Double
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
            var value: Double? = null
            try {
                value = text.toString().toDouble()
                if (value !in range) {
                    value = null
                    throw Exception()
                }
            } catch (e: Exception) {
                // uiAction
                laboratoryResearchRecordListener.makeToast(
                    resources.getString(toastErrorParamRes),
                    range
                )
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