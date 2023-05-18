package com.example.data.repositories.laboratory_research.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.laboratory_research.sources.entities.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitLaboratoryResearchSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), LaboratoryResearchSource {

    private val laboratoryResearchApi = retrofit.create(LaboratoryResearchApi::class.java)

    override suspend fun getLaboratoryResearches(): List<GetLaboratoryResearchResponseDataEntity> =
        wrapRetrofitExceptions { laboratoryResearchApi.getLaboratoryResearches().analyses }

    override suspend fun createLaboratoryResearch(createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity)
            : CreateLaboratoryResearchResponseDataEntity =
        wrapRetrofitExceptions {
            laboratoryResearchApi.createLaboratoryResearch(
                createLaboratoryResearchRequestDataEntity
            )
        }

    override suspend fun updateLaboratoryResearch(
        laboratoryResearchId: Long,
        updateLaboratoryResearchRequestDataEntity: UpdateLaboratoryResearchRequestDataEntity
    ): UpdateLaboratoryResearchResponseDataEntity =
        wrapRetrofitExceptions {
            laboratoryResearchApi.updateLaboratoryResearch(
                laboratoryResearchId,
                updateLaboratoryResearchRequestDataEntity
            )
        }
}