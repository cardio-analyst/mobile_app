package com.example.data.base

import android.content.Context
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ContextResources @Inject constructor(
    @ApplicationContext val applicationContext: Context
) {

    /**
     * Get string resource content by its identifier.
     */
    fun getString(messageRes: Int, vararg args: Any): String =
        applicationContext.getString(messageRes, *args)
}