package `is`.ulstu.foundation.navigator

import `is`.ulstu.foundation.utils.ResourceActions
import `is`.ulstu.foundation.views.BaseScreen

class IntermediateNavigator : Navigator {

    private val targetNavigator = ResourceActions<Navigator>()

    override fun launch(screen: BaseScreen) = targetNavigator {
        it.launch(screen)
    }

    override fun addFragmentToScreen(containerId: Int, screen: BaseScreen) = targetNavigator {
        it.addFragmentToScreen(containerId, screen)
    }

    override fun goBack(result: Any?) = targetNavigator {
        it.goBack(result)
    }

    override fun getBackstackFragmentCount(): Int =
        targetNavigator.resource?.getBackstackFragmentCount() ?: 0

    fun setTarget(navigator: Navigator?) {
        targetNavigator.resource = navigator
    }

    fun clear() {
        targetNavigator.clear()
    }
}