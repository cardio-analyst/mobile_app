package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.questionnaires_list.presentation.QuestionnaireRouter
import com.example.questionnaires_list.presentation.QuestionnairesFragmentDirections
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireNavigation @Inject constructor(
    private val tabsController: TabsController,
) : QuestionnaireRouter {

    override fun launchDiseasesScreen() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToNavigationDiseases()
        tabsController.navigate(direction)
    }

    override fun launchBasicIndicatorsScreen() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToNavigationBasicIndicators()
        tabsController.navigate(direction)
    }

    override fun launchLaboratoryResearchScreen() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToNavigationLaboratoryResearch()
        tabsController.navigate(direction)
    }

    override fun launchLifestyleScreen() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToNavigationLifestyle()
        tabsController.navigate(direction)
    }

}