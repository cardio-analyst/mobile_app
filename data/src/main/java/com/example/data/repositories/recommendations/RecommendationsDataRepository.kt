package com.example.data.repositories.recommendations

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.recommendations.sources.RecommendationsSource
import com.example.data.repositories.recommendations.sources.entities.GetRecommendationsResponseDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportRequestDataEntity
import com.example.data.repositories.recommendations.sources.entities.SendReportResponseDataEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecommendationsDataRepository @Inject constructor(
    private val recommendationsSource: RecommendationsSource,
    private val userRepository: IUserDataRepository,
) : IRecommendationsDataRepository {

    // --- Lazy Repository Flows for observers

    private val recommendationsLazyFlowSubject =
        LazyFlowSubject<Unit, List<GetRecommendationsResponseDataEntity>> {
            doGetLaboratoryResearches()
        }

    private val sendReportToEmailLazyFlowSubject =
        LazyFlowSubject<SendReportRequestDataEntity, SendReportResponseDataEntity> { sendReportRequestEntity ->
            doSendReportToEmail(sendReportRequestEntity)
        }


    override fun getRecommendations(): Flow<ResultState<List<GetRecommendationsResponseDataEntity>>> =
        recommendationsLazyFlowSubject.listen(Unit)

    private suspend fun doGetLaboratoryResearches(): List<GetRecommendationsResponseDataEntity> =
        wrapBackendExceptions(userRepository) {
            recommendationsSource.getRecommendations()
        }

    override fun reloadGetRecommendations() {
        recommendationsLazyFlowSubject.reloadAll()
    }


    override fun sendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity): Flow<ResultState<SendReportResponseDataEntity>> =
        sendReportToEmailLazyFlowSubject.listen(sendReportRequestDataEntity)

    private suspend fun doSendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity): SendReportResponseDataEntity =
        wrapBackendExceptions(userRepository) {
            recommendationsSource.sendReportToEmail(sendReportRequestDataEntity)
        }

    override fun reloadSendReportToEmail(sendReportRequestDataEntity: SendReportRequestDataEntity) {
        sendReportToEmailLazyFlowSubject.reloadArgument(sendReportRequestDataEntity)
    }

}