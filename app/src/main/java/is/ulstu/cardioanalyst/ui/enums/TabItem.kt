package `is`.ulstu.cardioanalyst.ui.enums

import `is`.ulstu.cardioanalyst.R
import `is`.ulstu.cardioanalyst.app.Singletons

/**
 * Enum class for navigation tabs
 */
enum class TabItem(val position: Int, val tabName: String) {
    DISEASES(0, Singletons.getString(R.string.title_diseases)),
    HEART_INDICATORS(1, Singletons.getString(R.string.title_basic_indicators)),
    LIFESTYLE(2, Singletons.getString(R.string.title_lifestyle)),
    EXTRA(3, Singletons.getString(R.string.title_laboratory_research)),
    RECOMMENDATION(4, Singletons.getString(R.string.title_recommendation)),
    PROFILE(5, Singletons.getString(R.string.title_profile))
}