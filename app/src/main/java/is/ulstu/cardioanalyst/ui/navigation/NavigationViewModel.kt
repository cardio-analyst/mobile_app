package `is`.ulstu.cardioanalyst.ui.navigation

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.models.settings.AppSettings
import `is`.ulstu.cardioanalyst.models.settings.UserSettings
import `is`.ulstu.cardioanalyst.ui.basic_indicators.BasicIndicatorsFragment
import `is`.ulstu.cardioanalyst.ui.diseases.DiseasesFragment
import `is`.ulstu.cardioanalyst.ui.enums.TabItem
import `is`.ulstu.cardioanalyst.ui.laboratory_research.LaboratoryResearchFragment
import `is`.ulstu.cardioanalyst.ui.lifestyle.LifestyleFragment
import `is`.ulstu.cardioanalyst.ui.profile.ProfileFragment
import `is`.ulstu.cardioanalyst.ui.recommendations.RecommendationsFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.views.BaseViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavigationViewModel @Inject constructor(
    private val navigator: Navigator,
    userSettings: UserSettings,
    private val diseasesFragment: DiseasesFragment,
    private val profileFragment: ProfileFragment,
    private val basicIndicatorsFragment: BasicIndicatorsFragment,
    private val laboratoryResearchFragment: LaboratoryResearchFragment,
    private val recommendationsFragment: RecommendationsFragment,
    private val lifestyleFragment: LifestyleFragment,
    private val appSettings: AppSettings,
) : BaseViewModel(navigator, userSettings) {

    var currentTab = TabItem.values().find { it.tabName == appSettings.getLastTab() }
        ?: TabItem.DISEASES
        private set

    fun onChooseSettingsMode(tabItem: TabItem) {
        val fragment = when (tabItem) {
            TabItem.DISEASES -> {
                currentTab = TabItem.DISEASES
                diseasesFragment
            }
            TabItem.PROFILE -> {
                currentTab = TabItem.PROFILE
                profileFragment
            }
            TabItem.HEART_INDICATORS -> {
                currentTab = TabItem.HEART_INDICATORS
                basicIndicatorsFragment
            }
            TabItem.LIFESTYLE -> {
                currentTab = TabItem.LIFESTYLE
                lifestyleFragment
            }
            TabItem.EXTRA -> {
                currentTab = TabItem.EXTRA
                laboratoryResearchFragment
            }
            TabItem.RECOMMENDATION -> {
                currentTab = TabItem.RECOMMENDATION
                recommendationsFragment
            }
            else -> {
                throw Exception("Not correct mode")
            }
        }
        navigator.addFragmentToScreen(R.id.tabFragmentContainer, fragment)
    }

    fun onStop() {
        appSettings.setLastTab(currentTab.tabName)
        val fragment = when (currentTab) {
            TabItem.DISEASES -> {
                diseasesFragment
            }
            TabItem.PROFILE -> {
                profileFragment
            }
            TabItem.HEART_INDICATORS -> {
                basicIndicatorsFragment
            }
            TabItem.LIFESTYLE -> {
                lifestyleFragment
            }
            TabItem.EXTRA -> {
                laboratoryResearchFragment
            }
            TabItem.RECOMMENDATION -> {
                recommendationsFragment
            }
            else -> {
                throw Exception("Not correct mode")
            }
        }
        navigator.removeFragment(fragment)
    }

}