package com.example.common

import android.content.Context

object Singletons {
    private lateinit var appContext: Context

    // --- context methods

    fun getString(id: Int) = appContext.resources.getString(id)

    fun getString(id: Int, vararg formatArgs: Any) =
        appContext.resources.getString(id, *formatArgs)

    fun init(appContext: Context) {
        Singletons.appContext = appContext
    }
}