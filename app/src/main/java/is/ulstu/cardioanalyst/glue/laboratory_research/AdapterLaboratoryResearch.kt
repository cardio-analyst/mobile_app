package `is`.ulstu.cardioanalyst.glue.laboratory_research

import com.example.common.flows.ResultState
import com.example.data.repositories.laboratory_research.LaboratoryResearchDataRepository
import com.example.laboratory_research.domain.LaboratoryResearchRepository
import com.example.laboratory_research.domain.entities.*
import `is`.ulstu.cardioanalyst.glue.laboratory_research.mappers.*
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterLaboratoryResearch @Inject constructor(
    private val laboratoryResearchRepository: LaboratoryResearchDataRepository
) : LaboratoryResearchRepository {

    override fun getLaboratoryResearches(): Flow<ResultState<List<GetLaboratoryResearchResponseEntity>>> =
        laboratoryResearchRepository.getLaboratoryResearches().map { resultState ->
            resultState.map { getLaboratoryResearchResponseDataEntities ->
                getLaboratoryResearchResponseDataEntities.map { getLaboratoryResearchResponseDataEntity ->
                    getLaboratoryResearchResponseDataEntity.toGetLaboratoryResearchResponseEntity()
                }
            }
        }

    override fun createLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity): Flow<ResultState<CreateLaboratoryResearchResponseEntity>> =
        laboratoryResearchRepository.createLaboratoryResearch(createLaboratoryResearchRequestEntity.toCreateLaboratoryResearchRequestDataEntity())
            .map { resultState ->
                resultState.map { createLaboratoryResearchResponseDataEntity ->
                    createLaboratoryResearchResponseDataEntity.toCreateLaboratoryResearchResponseEntity()
                }
            }

    override fun updateLaboratoryResearch(updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity): Flow<ResultState<UpdateLaboratoryResearchResponseEntity>> =
        laboratoryResearchRepository.updateLaboratoryResearch(updateLaboratoryResearchIdEntity.toUpdateLaboratoryResearchIdDataEntity())
            .map { resultState ->
                resultState.map { updateLaboratoryResearchResponseDataEntity ->
                    updateLaboratoryResearchResponseDataEntity.toUpdateLaboratoryResearchResponseEntity()
                }
            }

    override fun reloadGetLaboratoryResearches() =
        laboratoryResearchRepository.reloadGetLaboratoryResearches()

    override fun reloadCreateLaboratoryResearch(createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity) =
        laboratoryResearchRepository.reloadCreateLaboratoryResearch(
            createLaboratoryResearchRequestEntity.toCreateLaboratoryResearchRequestDataEntity()
        )

    override fun reloadUpdateLaboratoryResearch(updateLaboratoryResearchIdEntity: UpdateLaboratoryResearchIdEntity) =
        laboratoryResearchRepository.reloadUpdateLaboratoryResearch(
            updateLaboratoryResearchIdEntity.toUpdateLaboratoryResearchIdDataEntity()
        )

}