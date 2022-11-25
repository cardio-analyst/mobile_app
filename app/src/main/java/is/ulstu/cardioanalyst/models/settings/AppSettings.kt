package `is`.ulstu.cardioanalyst.models.settings

interface AppSettings {

    /**
     * Get user access token
     */
    fun getUserAccountAccessToken(): String?

    /**
     * Set user access token
     * @param userAccessToken [String]
     */
    fun setUserAccountAccessToken(userAccessToken: String?)

    /**
     * Get auth token of the current logged-in user.
     * @return JWT refresh token [String]
     */
    fun getCurrentRefreshToken(): String?

    /**
     * Set auth token of the logged-in user.
     * @param userRefreshToken [String]
     */
    fun setCurrentRefreshToken(userRefreshToken: String?)

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