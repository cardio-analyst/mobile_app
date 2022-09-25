package `is`.ulstu.foundation.uiactions

import android.os.IBinder
import android.widget.EditText

interface UiHelper {
    /**
     * Get size pair<Width, Height> of screen params
     */
    fun getRealScreenSize(): Pair<Int, Int>

    /**
     * Get dimension in dp by [dimensionInPixel]
     */
    fun getDimensionInDp(dimensionInPixel: Float): Int

    /**
     * Hide keyboard
     */
    fun hideInput(token: IBinder)
}