package `is`.ulstu.cardioanalyst.presentation.nav_routers

import com.example.presentation.BaseRouter
import com.example.questionnaires_list.presentation.QuestionnaireRouter
import com.example.questionnaires_list.presentation.QuestionnairesFragmentDirections
import `is`.ulstu.cardioanalyst.presentation.controllers.TabsController
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class QuestionnaireNavigation @Inject constructor(
    private val tabsController: TabsController,
    private val baseNavigation: BaseNavigation,
) : QuestionnaireRouter, BaseRouter by baseNavigation {

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

    override fun launchReportScreen() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToSendingReportFragment()
        tabsController.navigate(direction)
    }

    override fun launchStenocardiaSymptomsTest() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToStenocardiaSymptomsTestFragment()
        tabsController.navigate(direction)
    }

    override fun launchTreatmentAdherenceTest() {
        val direction =
            QuestionnairesFragmentDirections.actionQuestionnairesFragmentToTreatmentAdherenceTestFragment()
        tabsController.navigate(direction)
    }

}