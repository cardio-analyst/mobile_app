package com.example.data.repositories.diseases.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.diseases.sources.entities.DiseasesMainEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitDiseasesSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), DiseasesSource {

    private val diseasesApi = retrofit.create(DiseasesApi::class.java)

    override suspend fun getUserDiseases(): DiseasesMainEntity =
        wrapRetrofitExceptions { diseasesApi.getUserDiseases() }

    override suspend fun setUserDiseases(diseasesMainEntity: DiseasesMainEntity): DiseasesMainEntity =
        wrapRetrofitExceptions { diseasesApi.setUserDiseases(diseasesMainEntity) }
}