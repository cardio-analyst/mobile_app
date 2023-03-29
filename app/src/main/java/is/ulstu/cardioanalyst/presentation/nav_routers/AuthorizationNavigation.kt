package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.authorization.presentation.AuthorizationFragmentDirections
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.presentation.controllers.MainController
import com.example.authorization.presentation.AuthorizationRouter
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AuthorizationNavigation @Inject constructor(
    private val mainController: MainController,
) : AuthorizationRouter {

    override fun launchTabsScreen() = mainController.navigate(R.id.action_global_navigation_tabs)

    override fun launchRegistrationScreen() {
        val direction =
            AuthorizationFragmentDirections.actionNavigationAuthorizationToNavigationRegistration()
        mainController.navigate(direction)
    }

}