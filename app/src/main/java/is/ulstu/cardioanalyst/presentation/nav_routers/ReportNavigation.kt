package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.report.presentation.ReportRouter
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ReportNavigation @Inject constructor(
    private val tabsController: TabsController,
) : ReportRouter {
    override fun goBack() = tabsController.goBack()
}