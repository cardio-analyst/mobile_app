package `is`.ulstu.foundation.navigator

import `is`.ulstu.foundation.views.BaseScreen

/**
 * Navigation for your application
 */
interface Navigator {

    /**
     *
     * Launch a new screen at the top of back stack.
     */
    fun launch(screen: BaseScreen)

    /**
     * Add new fragment to the existing screen with [containerId]
     */
    fun addFragmentToScreen(containerId: Int, screen: BaseScreen)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    fun goBack(result: Any? = null)
}