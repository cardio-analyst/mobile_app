package `is`.ulstu.cardioanalyst.ui.navigation

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.ui.basic_indicators.BasicIndicatorsFragment
import `is`.ulstu.cardioanalyst.ui.diseases.DiseasesFragment
import `is`.ulstu.cardioanalyst.ui.enums.TabItem
import `is`.ulstu.cardioanalyst.ui.laboratory_research.LaboratoryResearchFragment
import `is`.ulstu.cardioanalyst.ui.lifestyle.LifestyleFragment
import `is`.ulstu.cardioanalyst.ui.profile.ProfileFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.views.BaseViewModel

class NavigationViewModel(
    private val navigator: Navigator,
) : BaseViewModel(navigator) {

    var currentTab = TabItem.values().find { it.tabName == Singletons.appSettings.getLastTab() }
        ?: TabItem.GENERAL_INFO
        private set

    fun onChooseSettingsMode(tabItem: TabItem) {
        val screen = when (tabItem) {
            TabItem.GENERAL_INFO -> {
                currentTab = TabItem.GENERAL_INFO
                DiseasesFragment.Screen()
            }
            TabItem.PROFILE -> {
                currentTab = TabItem.PROFILE
                ProfileFragment.Screen()
            }
            TabItem.HEART_INDICATORS -> {
                BasicIndicatorsFragment.Screen()
            }
            TabItem.LIFESTYLE -> {
                currentTab = TabItem.LIFESTYLE
                LifestyleFragment.Screen()
            }
            TabItem.EXTRA -> {
                currentTab = TabItem.EXTRA
                LaboratoryResearchFragment.Screen()
            }
            /*TabItem.RECOMMENDATION -> {
                PhotoSampleSettingsFragment.Screen()
            }*/
            else -> {
                throw Exception("Not correct mode")
            }
        }
        navigator.addFragmentToScreen(R.id.tabFragmentContainer, screen)
    }

    override fun onCleared() {
        Singletons.appSettings.setLastTab(currentTab.tabName)
        super.onCleared()
    }
}