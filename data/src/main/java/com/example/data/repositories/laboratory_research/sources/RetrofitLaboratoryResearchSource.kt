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

    override suspend fun getLaboratoryResearches(): List<GetLaboratoryResearchResponseEntity> =
        wrapRetrofitExceptions { laboratoryResearchApi.getLaboratoryResearches().analyses }

    override suspend fun createLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity)
            : CreateLaboratoryResearchResponseEntity =
        wrapRetrofitExceptions {
            laboratoryResearchApi.createLaboratoryResearch(
                createLaboratoryResearchRequestEntity
            )
        }

    override suspend fun updateLaboratoryResearch(
        laboratoryResearchId: Long,
        updateLaboratoryResearchRequestEntity: UpdateLaboratoryResearchRequestEntity
    ): UpdateLaboratoryResearchResponseEntity =
        wrapRetrofitExceptions {
            laboratoryResearchApi.updateLaboratoryResearch(
                laboratoryResearchId,
                updateLaboratoryResearchRequestEntity
            )
        }
}