package `is`.ulstu.cardioanalyst.models.settings

import android.content.Context
import android.content.SharedPreferences

/**
 * Implementation of [AppSettings] based on [SharedPreferences].
 */
class SharedPreferencesAppSettings(
    appContext: Context
) : AppSettings {

    private var userAccessToken: String? = null
    private val sharedPreferences = appContext.getSharedPreferences("settings", Context.MODE_PRIVATE)

    override fun getUserAccountAccessToken(): String? = userAccessToken

    override fun setUserAccountAccessToken(userAccessToken: String?) {
        this.userAccessToken = userAccessToken
    }

    override fun setCurrentRefreshToken(userRefreshToken: String?) {
        val editor = sharedPreferences.edit()
        if (userRefreshToken == null)
            editor.remove(USER_CURRENT_REFRESH_TOKEN)
        else
            editor.putString(USER_CURRENT_REFRESH_TOKEN, userRefreshToken)
        editor.apply()
    }

    override fun getCurrentRefreshToken(): String? =
        sharedPreferences.getString(USER_CURRENT_REFRESH_TOKEN, null)

    companion object {
        /**
         * Key for refresh token in [SharedPreferences]
         */
        private const val USER_CURRENT_REFRESH_TOKEN = "currentRefreshToken"
    }
}