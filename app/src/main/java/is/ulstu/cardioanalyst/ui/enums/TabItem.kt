package `is`.ulstu.cardioanalyst.ui.enums

import `is`.ulstu.cardioanalyst.App
import `is`.ulstu.cardioanalyst.R

private fun App.Companion.getTabName(id: Int) = appResources.getString(id)

enum class TabItem(val position: Int, val tabName: String) {
    GENERAL_INFO(0, App.getTabName(R.string.tab_item_diseases)),
    HEART_INDICATORS(1, App.getTabName(R.string.tab_item_heart_indicators)),
    LIFESTYLE(2, App.getTabName(R.string.tab_item_lifestyle)),
    EXTRA(3, App.getTabName(R.string.tab_item_extra)),
    RECOMMENDATION(4, App.getTabName(R.string.tab_item_recommendation)),
    PROFILE(5, App.getTabName(R.string.tab_item_profile))
}