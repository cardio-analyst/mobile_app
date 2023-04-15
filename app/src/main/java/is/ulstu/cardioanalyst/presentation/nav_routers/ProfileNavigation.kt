package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.profile.presentation.ProfileRouter
import `is`.ulstu.cardioanalyst.presentation.controllers.MainController
import `is`.ulstu.cardioanalyst.presentation.ui.tabs.TabsFragmentDirections
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProfileNavigation @Inject constructor(
    private val mainController: MainController,
) : ProfileRouter {

    override fun launchAuthorizationScreen() {
        val direction = TabsFragmentDirections.actionNavigationTabsToAuthGraph()
        mainController.navigate(direction)
    }
}