package `is`.ulstu.foundation.uiactions

import android.content.Context
import android.os.Build
import android.os.IBinder
import android.util.TypedValue
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.annotation.RequiresApi

class AndroidUiHelper(private val appContext: Context, private val windowManager: WindowManager) :
    UiHelper {
    @RequiresApi(Build.VERSION_CODES.R)
    override fun getRealScreenSize(): Pair<Int, Int> {
        val metrics = windowManager.currentWindowMetrics
        val bounds = metrics.bounds
        return Pair(bounds.width(), bounds.height())
    }

    override fun getDimensionInDp(dimensionInPixel: Float): Int {
        var dimensionInDp = 0
        dimensionInDp = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            dimensionInPixel,
            appContext.resources.displayMetrics
        ).toInt()
        return dimensionInDp
    }

    override fun hideInput(token: IBinder) {
        val a = appContext.getSystemService(Context.INPUT_METHOD_SERVICE)
        if (a is InputMethodManager) {
            a.hideSoftInputFromWindow(token, InputMethodManager.HIDE_NOT_ALWAYS)
        }
    }
}