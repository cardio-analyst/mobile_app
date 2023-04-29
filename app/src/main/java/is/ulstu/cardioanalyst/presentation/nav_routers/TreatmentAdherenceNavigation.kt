package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.treatment_adherence.presentation.TreatmentAdherenceRouter
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreatmentAdherenceNavigation @Inject constructor(
    private val tabsController: TabsController,
) : TreatmentAdherenceRouter {
    override fun goBack() = tabsController.goBack()
}