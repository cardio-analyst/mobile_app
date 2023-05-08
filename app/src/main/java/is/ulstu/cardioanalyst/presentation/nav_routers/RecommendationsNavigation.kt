package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.recommendations.presentation.RecommendationsFragmentDirections
import com.example.recommendations.presentation.RecommendationsRouter
import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationsNavigation @Inject constructor(
    private val tabsController: TabsController,
) : RecommendationsRouter {

    override fun navigateToQuestionnaires() =
        tabsController.navigateToTab(R.id.questionnaires_graph)

    override fun launchFeedbackScreen() {
        val direction =
            RecommendationsFragmentDirections.actionRecommendationFragmentToFeedbackFragment()
        tabsController.navigate(direction)
    }
}