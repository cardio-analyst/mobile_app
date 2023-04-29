package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.registration.presentation.RegistrationRouter
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.presentation.controllers.MainController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RegistrationNavigation@Inject constructor(
    private val mainController: MainController,
) : RegistrationRouter {

    override fun launchTabsScreen() = mainController.navigate(R.id.action_global_navigation_tabs)

}