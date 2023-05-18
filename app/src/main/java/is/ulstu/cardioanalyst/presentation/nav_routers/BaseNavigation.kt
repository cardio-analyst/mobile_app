package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.presentation.BaseRouter
import com.example.presentation.uiactions.UiAction
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.presentation.controllers.MainController
import `is`.ulstu.cardioanalyst.presentation.ui.tabs.TabsFragmentDirections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class BaseNavigation @Inject constructor(
    private val uiAction: UiAction,
    private val mainController: MainController,
) : BaseRouter {

    override val onSessionExpired = {
        uiAction.toast(R.string.on_session_expired)
        val direction = TabsFragmentDirections.actionNavigationTabsToAuthGraph()
        mainController.navigate(direction)
    }
}