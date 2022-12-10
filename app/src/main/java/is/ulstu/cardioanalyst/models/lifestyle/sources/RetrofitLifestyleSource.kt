package `is`.ulstu.cardioanalyst.models.lifestyle.sources

import `is`.ulstu.cardioanalyst.models.lifestyle.sources.entities.LifestyleMainEntity
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitLifestyleSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), LifestyleSource {

    private val lifestyleApi = retrofit.create(LifestyleApi::class.java)

    override suspend fun getUserLifestyle(): LifestyleMainEntity =
        wrapRetrofitExceptions { lifestyleApi.getUserLifestyle() }

    override suspend fun setUserLifestyle(lifestyleMainEntity: LifestyleMainEntity): LifestyleMainEntity =
        wrapRetrofitExceptions { lifestyleApi.setUserLifestyle(lifestyleMainEntity) }
}