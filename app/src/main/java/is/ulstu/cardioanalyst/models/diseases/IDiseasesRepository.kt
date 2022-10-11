package `is`.ulstu.cardioanalyst.models.diseases

import `is`.ulstu.foundation.model.Repository

interface IDiseasesRepository : Repository {
    /**
     * Get all available diseases to check
     */
    fun getUserDiseases(token: String): MutableMap<String, Boolean>

    /**
     * Set user diseases
     */
    fun setUserDiseases(token: String, diseasesMap: Map<String, Boolean>)
}