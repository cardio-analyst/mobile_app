package `is`.ulstu.cardioanalyst.ui.navigation

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.ui.enums.TabItem
import `is`.ulstu.cardioanalyst.ui.diseases.DiseasesFragment
import `is`.ulstu.cardioanalyst.ui.profile.ProfileFragment
import `is`.ulstu.foundation.navigator.Navigator
import `is`.ulstu.foundation.views.BaseViewModel

class NavigationViewModel(
    private val navigator: Navigator,
) : BaseViewModel(navigator) {

    fun onChooseSettingsMode(tabItem: TabItem) {
        val screen = when (tabItem) {
            TabItem.GENERAL_INFO -> {
                DiseasesFragment.Screen()
            }
            TabItem.PROFILE -> {
                ProfileFragment.Screen()
            }
            /*TabItem.HEART_INDICATORS -> {
                DesignSettingsFragment.Screen()
            }
            TabItem.LIFESTYLE -> {
                PhotoSampleSettingsFragment.Screen()
            }
            TabItem.EXTRA -> {
                PhotoSampleSettingsFragment.Screen()
            }
            TabItem.RECOMMENDATION -> {
                PhotoSampleSettingsFragment.Screen()
            }*/
            else -> {
                throw Exception("Not correct mode")
            }
        }
        navigator.addFragmentToScreen(R.id.tabFragmentContainer, screen)
    }
}