package `is`.ulstu.cardioanalyst.models.recommendations

import `is`.ulstu.cardioanalyst.models.recommendations.sources.RecommendationsSource
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.GetRecommendationsResponseEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportRequestEntity
import `is`.ulstu.cardioanalyst.models.recommendations.sources.entities.SendReportResponseEntity
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.foundation.model.Result
import `is`.ulstu.foundation.utils.LazyFlowSubject
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationsDBRepository @Inject constructor(
    private val recommendationsSource: RecommendationsSource,
    private val userRepository: IUserRepository,
) : IRecommendationsRepository {

    // --- Lazy Repository Flows for observers

    private val recommendationsLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetRecommendationsResponseEntity>> {
            doGetLaboratoryResearches()
        }

    private val sendReportToEmailLazyFlowSubject =
        LazyFlowSubject<SendReportRequestEntity, SendReportResponseEntity> { sendReportRequestEntity ->
            doSendReportToEmail(sendReportRequestEntity)
        }


    override fun getRecommendations(): Flow<Result<List<GetRecommendationsResponseEntity>>> =
        recommendationsLazyFlowSubject.listen(Unit)

    private suspend fun doGetLaboratoryResearches(): List<GetRecommendationsResponseEntity> =
        wrapBackendExceptions(userRepository) {
            recommendationsSource.getRecommendations()
        }

    override fun reloadGetRecommendations() {
        recommendationsLazyFlowSubject.reloadAll()
    }


    override fun sendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): Flow<Result<SendReportResponseEntity>> =
        sendReportToEmailLazyFlowSubject.listen(sendReportRequestEntity)

    private suspend fun doSendReportToEmail(sendReportRequestEntity: SendReportRequestEntity): SendReportResponseEntity =
        wrapBackendExceptions(userRepository) {
            recommendationsSource.sendReportToEmail(sendReportRequestEntity)
        }

    override fun reloadSendReportToEmail(sendReportRequestEntity: SendReportRequestEntity) {
        sendReportToEmailLazyFlowSubject.reloadArgument(sendReportRequestEntity)
    }

}