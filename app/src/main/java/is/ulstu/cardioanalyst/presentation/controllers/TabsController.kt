package `is`.ulstu.cardioanalyst.presentation.controllers

import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TabsController @Inject constructor() {

    var tabsNavController: NavController? = null

    /**
     * Navigate by direction
     */
    fun navigate(direction: NavDirections) {
        tabsNavController?.navigate(direction)
    }

    /**
     * Navigate by [destinationId]
     */
    fun navigate(@IdRes destinationId: Int) {
        tabsNavController?.navigate(resId = destinationId)
    }

    /**
     * PopBackStack method for current navController
     */
    fun goBack() {
        tabsNavController?.popBackStack()
    }
}