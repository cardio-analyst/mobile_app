package `is`.ulstu.foundation.navigator

import `is`.ulstu.foundation.utils.ResourceActions
import androidx.fragment.app.Fragment
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntermediateNavigator @Inject constructor() : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    override fun launch(fragment: Fragment) = targetNavigator {
        it.launch(fragment)
    }

    override fun addFragmentToScreen(containerId: Int, fragment: Fragment) = targetNavigator {
        it.addFragmentToScreen(containerId, fragment)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result)
    }

    override fun getBackstackFragmentCount(): Int =
        targetNavigator.resource?.getBackstackFragmentCount() ?: 0

    override fun removeFragment(fragment: Fragment) {
        targetNavigator.resource?.removeFragment(fragment)
    }

    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    fun clear() {
        targetNavigator.clear()
    }
}