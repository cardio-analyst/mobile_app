package `is`.ulstu.cardioanalyst.presentation.controllers

import android.os.Parcelable
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.NavDirections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MainController @Inject constructor() {

    var mainNavController: NavController? = null

    /**
     * Navigate by direction
     */
    fun navigate(direction: NavDirections) {
        mainNavController?.navigate(direction)
    }

    /**
     * Navigate by [destinationId]
     */
    fun navigate(@IdRes destinationId: Int) {
        mainNavController?.navigate(resId = destinationId)
    }

    /**
     * Navigate by [destinationId] and [args]
     */
    fun navigate(@IdRes destinationId: Int, args: Parcelable) {
        /*mainNavController?.navigate(
            resId = destinationId,
            args = Bundle().apply {
                putParcelable(ARG_SCREEN, args)
            }
        )*/
    }

    /**
     * PopBackStack method for current navController
     */
    fun goBack() {
        mainNavController?.popBackStack()
    }
}