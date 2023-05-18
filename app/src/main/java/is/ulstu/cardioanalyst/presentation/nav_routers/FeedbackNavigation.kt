package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.feedback.presentation.FeedbackRouter
import com.example.presentation.BaseRouter
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FeedbackNavigation @Inject constructor(
    private val tabsController: TabsController,
    private val baseNavigation: BaseNavigation,
) : FeedbackRouter, BaseRouter by baseNavigation {
    override fun goBack() = tabsController.goBack()
}