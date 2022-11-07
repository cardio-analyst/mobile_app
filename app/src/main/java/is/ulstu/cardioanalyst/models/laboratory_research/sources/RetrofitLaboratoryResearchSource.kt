package `is`.ulstu.cardioanalyst.models.laboratory_research.sources

import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.*
import `is`.ulstu.cardioanalyst.sources.base.BaseRetrofitSource
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig

class RetrofitLaboratoryResearchSource(
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