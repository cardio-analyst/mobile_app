package com.example.data.settings

import android.content.Context
import android.content.SharedPreferences
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Implementation of [UserSettings] based on [SharedPreferences].
 */
@Singleton
class SharedPreferencesUserSettings @Inject constructor(
    @ApplicationContext appContext: Context
) : UserSettings {

    private val sharedPreferences =
        appContext.getSharedPreferences("userSettings", Context.MODE_PRIVATE)
    private var userAccessToken: String? = null

    override fun getUserAccountAccessToken(): String? = userAccessToken

    override fun setUserAccountAccessToken(userAccessToken: String?) {
        this.userAccessToken = userAccessToken
    }

    override fun setCurrentRefreshToken(userRefreshToken: String?) {
        val editor = sharedPreferences.edit()
        if (userRefreshToken == null)
            editor.remove(USER_CURRENT_REFRESH_TOKEN)
        else
            editor.putString(
                USER_CURRENT_REFRESH_TOKEN,
                userRefreshToken
            )
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