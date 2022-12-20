package `is`.ulstu.cardioanalyst.models.settings

interface AppSettings {

    /**
     * Get last tab user visit.
     * @return tab name [String]
     */
    fun getLastTab(): String?

    /**
     * Set last tab user visit.
     * @param tabName [String]
     */
    fun setLastTab(tabName: String)

}