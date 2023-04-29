package `is`.ulstu.cardioanalyst.glue.diseases

import com.example.common.flows.ResultState
import com.example.data.repositories.diseases.DiseasesDataRepository
import com.example.diseases.domain.DiseasesRepository
import com.example.diseases.domain.entities.DiseasesEntity
import `is`.ulstu.cardioanalyst.glue.diseases.mappers.toDiseasesDataEntity
import `is`.ulstu.cardioanalyst.glue.diseases.mappers.toDiseasesEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterDiseasesRepository @Inject constructor(
    private val diseasesRepository: DiseasesDataRepository,
) : DiseasesRepository {

    override fun getUserDiseases(): Flow<ResultState<DiseasesEntity>> =
        diseasesRepository.getUserDiseases().map { resultState ->
            resultState.map { diseasesDataEntity ->
                diseasesDataEntity.toDiseasesEntity()
            }
        }

    override fun setUserDiseases(diseasesEntity: DiseasesEntity): Flow<ResultState<DiseasesEntity>> =
        diseasesRepository.setUserDiseases(diseasesEntity.toDiseasesDataEntity())
            .map { resultState ->
                resultState.map { diseasesDataEntity ->
                    diseasesDataEntity.toDiseasesEntity()
                }
            }

    override fun reloadGetDiseasesUserRequest() = diseasesRepository.reloadGetDiseasesUserRequest()

    override fun reloadSetDiseasesUserRequest(diseasesEntity: DiseasesEntity) =
        diseasesRepository.reloadSetDiseasesUserRequest(diseasesEntity.toDiseasesDataEntity())

}