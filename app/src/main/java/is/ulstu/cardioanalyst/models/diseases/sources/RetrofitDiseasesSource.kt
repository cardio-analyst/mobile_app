package `is`.ulstu.cardioanalyst.models.diseases.sources

import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig

class RetrofitDiseasesSource(
    config: RetrofitConfig
) : BaseRetrofitSource(config), DiseasesSource {

    private val diseasesApi = retrofit.create(DiseasesApi::class.java)

    override suspend fun getUserDiseases(): DiseasesMainEntity =
        wrapRetrofitExceptions { diseasesApi.getUserDiseases() }

    override suspend fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): DiseasesMainEntity =
        wrapRetrofitExceptions { diseasesApi.setUserDiseases(diseasesMainEntity) }
}