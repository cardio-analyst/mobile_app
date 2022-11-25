package `is`.ulstu.cardioanalyst.sources

import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.BasicIndicatorsSource
import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.LaboratoryResearchSource
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.LifestyleSource
import `is`.ulstu.cardioanalyst.models.users.sources.UsersSource

/**
 * Factory class for all network sources.
 */
interface SourcesProvider {

    /**
     * Create [UsersSource] which is responsible for reading/writing
     * user accounts data.
     */
    fun getUsersSource(): UsersSource

    /**
     * Create [DiseasesSource] which is responsible for reading/writing
     * user diseases data.
     */
    fun getDiseasesSource(): DiseasesSource

    /**
     * Create [BasicIndicatorsSource] which is responsible for reading/writing
     * user basicIndicators data.
     */
    fun getBasicIndicatorsSource(): BasicIndicatorsSource

    /**
     * Create [LifestyleSource] which is responsible for reading/writing
     * user lifestyle data.
     */
    fun getLifestyleSource(): LifestyleSource

    /**
     * Create [LaboratoryResearchSource] which is responsible for reading/writing
     * user laboratoryResearches data.
     */
    fun getLaboratoryResearchSource(): LaboratoryResearchSource

}