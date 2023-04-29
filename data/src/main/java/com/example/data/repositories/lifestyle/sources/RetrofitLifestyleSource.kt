package com.example.data.repositories.lifestyle.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.lifestyle.sources.entities.LifestyleDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitLifestyleSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), LifestyleSource {

    private val lifestyleApi = retrofit.create(LifestyleApi::class.java)

    override suspend fun getUserLifestyle(): LifestyleDataEntity =
        wrapRetrofitExceptions { lifestyleApi.getUserLifestyle() }

    override suspend fun setUserLifestyle(lifestyleDataEntity: LifestyleDataEntity): LifestyleDataEntity =
        wrapRetrofitExceptions {
            lifestyleApi.setUserLifestyle(lifestyleDataEntity)
            lifestyleDataEntity
        }
}