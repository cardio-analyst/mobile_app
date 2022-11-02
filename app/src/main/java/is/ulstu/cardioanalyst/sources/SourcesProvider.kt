package `is`.ulstu.cardioanalyst.sources

import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
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
     * Create [UsersSource] which is responsible for reading/writing
     * user diseases data.
     */
    fun getDiseasesSource(): DiseasesSource

}