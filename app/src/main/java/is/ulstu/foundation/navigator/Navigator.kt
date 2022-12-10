package `is`.ulstu.foundation.navigator

import androidx.fragment.app.Fragment

/**
 * Navigation for your application
 */
interface Navigator {

    /**
     * Launch a new screen at the top of back stack.
     */
    fun launch(fragment: Fragment)

    /**
     * Add new fragment to the existing screen with [containerId]
     */
    fun addFragmentToScreen(containerId: Int, fragment: Fragment)

    /**
     * Go back to the previous screen and optionally send some results.
     */
    fun goBack(result: Any? = null)

    /**
     * Get backstack fragments count
     */
    fun getBackstackFragmentCount(): Int

    /**
     * Remove child fragment for correct redraw fragment view after backPressed
     */
    fun removeFragment(fragment: Fragment)
}