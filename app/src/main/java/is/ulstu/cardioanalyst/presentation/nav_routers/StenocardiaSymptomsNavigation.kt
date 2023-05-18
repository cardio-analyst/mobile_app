package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.stenocardia_symptoms.presentation.StenocardiaSymptomsRouter
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StenocardiaSymptomsNavigation @Inject constructor(
    private val tabsController: TabsController,
) : StenocardiaSymptomsRouter {
    override fun goBack() = tabsController.goBack()
}