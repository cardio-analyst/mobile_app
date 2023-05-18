package `is`.ulstu.cardioanalyst.glue.recommendations

import com.example.common.flows.ResultState
import com.example.data.repositories.recommendations.IRecommendationsDataDataRepository
import com.example.recommendations.domain.RecommendationsRepository
import com.example.recommendations.domain.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.glue.recommendations.mappers.toGetRecommendationsResponseEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterRecommendationsRepository @Inject constructor(
    private val recommendationsRepository: IRecommendationsDataDataRepository
) : RecommendationsRepository {

    override fun getRecommendations(): Flow<ResultState<List<GetRecommendationsResponseEntity>>> =
        recommendationsRepository.getRecommendations().map { resultState ->
            resultState.map { getRecommendationsResponseDataEntities ->
                getRecommendationsResponseDataEntities.map { getRecommendationsResponseDataEntity ->
                    getRecommendationsResponseDataEntity.toGetRecommendationsResponseEntity()
                }
            }
        }

    override fun reloadGetRecommendations() =
        recommendationsRepository.reloadGetRecommendations()
}