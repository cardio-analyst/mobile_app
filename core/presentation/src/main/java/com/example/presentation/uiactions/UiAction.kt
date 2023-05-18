package com.example.presentation.uiactions

import androidx.annotation.StringRes

/**
 * Common actions that can be performed in the view-model
 */
interface UiAction {

    /**
     * Display a simple toast message.
     */
    fun toast(message: String)

    /**
     * Display a simple toast message by Resource Id
     */
    fun toast(@StringRes messageRes: Int, vararg args: Any)

    /**
     * Get string resource content by its identifier.
     */
    fun getString(messageRes: Int, vararg args: Any): String

}