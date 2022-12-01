package `is`.ulstu.cardioanalyst.models.recommendations

import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.entities.GetLaboratoryResearchResponseEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow

class RecommendationsDBRepository : IRecommendationsRepository {

    private val recommendationsSource = Singletons.recommendationsSource

    // --- Lazy Repository Flows for observers

    private val recommendationsLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetRecommendationsResponseEntity>> {
            doGetLaboratoryResearches()
        }

    override fun getRecommendations(): Flow<Result<List<GetRecommendationsResponseEntity>>> =
        recommendationsLazyFlowSubject.listen(Unit)

    private suspend fun doGetLaboratoryResearches(): List<GetRecommendationsResponseEntity> =
        wrapBackendExceptions {
            recommendationsSource.getRecommendations()
        }

    override fun reloadGetRecommendations() {
        recommendationsLazyFlowSubject.reloadAll()
    }
}