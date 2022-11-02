package `is`.ulstu.cardioanalyst.sources.base

import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
import `is`.ulstu.cardioanalyst.models.diseases.sources.RetrofitDiseasesSource
import `is`.ulstu.cardioanalyst.models.users.sources.RetrofitUsersSource
import `is`.ulstu.cardioanalyst.models.users.sources.UsersSource
import `is`.ulstu.cardioanalyst.sources.SourcesProvider

/**
 * Creating sources based on Retrofit + Moshi.
 */
class RetrofitSourcesProvider(
    private val config: RetrofitConfig
) : SourcesProvider {

    override fun getUsersSource(): UsersSource {
        return RetrofitUsersSource(config)
    }

    override fun getDiseasesSource(): DiseasesSource {
        return RetrofitDiseasesSource(config)
    }

}