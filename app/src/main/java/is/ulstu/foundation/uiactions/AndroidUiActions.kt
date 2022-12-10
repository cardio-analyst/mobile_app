package `is`.ulstu.foundation.uiactions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidUiActions @Inject constructor(
    @ApplicationContext private val appContext: Context
) : UiActions {

    override fun toast(message: String) {
        Toast.makeText(appContext, message, Toast.LENGTH_SHORT).show()
    }

    override fun toast(@StringRes messageRes: Int, vararg args: Any) {
        toast(getString(messageRes, *args))
    }

    override fun getString(@StringRes messageRes: Int, vararg args: Any): String {
        return appContext.getString(messageRes, *args)
    }

}