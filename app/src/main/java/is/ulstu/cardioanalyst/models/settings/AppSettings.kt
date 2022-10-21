package `is`.ulstu.cardioanalyst.models.settings

interface AppSettings {

    /**
     * Get user access token
     */
    fun getUserAccountAccessToken(): String?

    /**
     * Set user access token
     */
    fun setUserAccountAccessToken(userAccessToken: String?)

    /**
     * Get auth token of the current logged-in user.
     */
    fun getCurrentRefreshToken(): String?

    /**
     * Set auth token of the logged-in user.
     */
    fun setCurrentRefreshToken(token: String?)
}