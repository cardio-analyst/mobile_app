package `is`.ulstu.cardioanalyst.models.recommendations

import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.foundation.model.Repository
import `is`.ulstu.foundation.model.Result
import kotlinx.coroutines.flow.Flow

interface IRecommendationsRepository : Repository {

    /**
     * Get recommendations
     * @return [Flow]
     */
    fun getRecommendations(): Flow<Result<List<GetRecommendationsResponseEntity>>>

    /**
     * Reload getting recommendations
     */
    fun reloadGetRecommendations()

}