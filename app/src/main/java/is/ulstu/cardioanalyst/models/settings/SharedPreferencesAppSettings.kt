package `is`.ulstu.cardioanalyst.models.settings

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [AppSettings] based on [SharedPreferences].
 */
@Singleton
class SharedPreferencesAppSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : AppSettings {

    private val sharedPreferences =
        appContext.getSharedPreferences("appSettings", Context.MODE_PRIVATE)


    override fun getLastTab(): String? =
        sharedPreferences.getString(USER_LAST_TAB_USER_VISIT, null)

    override fun setLastTab(tabName: String) {
        val editor = sharedPreferences.edit()
        editor.putString(USER_LAST_TAB_USER_VISIT, tabName)
        editor.apply()
    }

    companion object {

        /**
         * Key for last tab user visit in [SharedPreferences]
         */
        private const val USER_LAST_TAB_USER_VISIT = "lastTabUserVisit"

    }
}