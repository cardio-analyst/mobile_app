package com.example.presentation.uiactions

import android.content.Context
import android.widget.Toast
import androidx.annotation.StringRes
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidUiAction @Inject constructor(
    @ApplicationContext private val appContext: Context
) : UiAction {

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