package `is`.ulstu.cardioanalyst.ui.enums

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons

/**
 * Enum class for navigation tabs
 */
enum class TabItem(val position: Int, val tabName: String) {
    DISEASES(0, Singletons.getString(R.string.tab_item_diseases)),
    HEART_INDICATORS(1, Singletons.getString(R.string.tab_item_heart_indicators)),
    LIFESTYLE(2, Singletons.getString(R.string.tab_item_lifestyle)),
    EXTRA(3, Singletons.getString(R.string.tab_item_extra)),
    RECOMMENDATION(4, Singletons.getString(R.string.tab_item_recommendation)),
    PROFILE(5, Singletons.getString(R.string.tab_item_profile))
}