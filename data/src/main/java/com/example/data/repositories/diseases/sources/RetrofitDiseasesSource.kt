package com.example.data.repositories.diseases.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.diseases.sources.entities.DiseasesDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitDiseasesSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), DiseasesSource {

    private val diseasesApi = retrofit.create(DiseasesApi::class.java)

    override suspend fun getUserDiseases(): DiseasesDataEntity =
        wrapRetrofitExceptions { diseasesApi.getUserDiseases() }

    override suspend fun setUserDiseases(diseasesDataEntity: DiseasesDataEntity): DiseasesDataEntity =
        wrapRetrofitExceptions {
            diseasesApi.setUserDiseases(diseasesDataEntity)
            diseasesDataEntity
        }
}