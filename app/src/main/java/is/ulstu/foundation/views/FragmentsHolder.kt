package `is`.ulstu.foundation.views

import `is`.ulstu.foundation.ActivityScopeViewModel

interface FragmentsHolder {
    fun notifyScreenUpdates()

    fun getActivityScopeViewModel(): ActivityScopeViewModel
}