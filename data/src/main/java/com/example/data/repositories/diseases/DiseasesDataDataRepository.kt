package com.example.data.repositories.diseases

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.diseases.sources.DiseasesSource
import com.example.data.repositories.diseases.sources.entities.DiseasesDataEntity
import com.example.data.repositories.users.IUserDataDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class DiseasesDataDataRepository @Inject constructor(
    private val diseasesSource: DiseasesSource,
    private val userRepository: IUserDataDataRepository,
) : IDiseasesDataDataRepository {

    // --- Lazy Repository Flows for observers

    private val diseasesLazyFlowSubject = LazyFlowSubject<Unit, DiseasesDataEntity> {
        doGetDiseases()
    }

    private val diseasesSaveLazyFlowSubject =
        LazyFlowSubject<DiseasesDataEntity, DiseasesDataEntity> { diseasesMainEntity ->
            doSetUserDiseases(diseasesMainEntity)
        }


    override fun getUserDiseases(): Flow<ResultState<DiseasesDataEntity>> =
        diseasesLazyFlowSubject.listen(Unit)

    private suspend fun doGetDiseases(): DiseasesDataEntity =
        wrapBackendExceptions(userRepository) {
            diseasesSource.getUserDiseases()
        }

    override fun reloadGetDiseasesUserRequest() {
        diseasesLazyFlowSubject.reloadAll()
    }


    override fun setUserDiseases(diseasesDataEntity: DiseasesDataEntity): Flow<ResultState<DiseasesDataEntity>> =
        diseasesSaveLazyFlowSubject.listen(diseasesDataEntity)

    private suspend fun doSetUserDiseases(diseasesDataEntity: DiseasesDataEntity): DiseasesDataEntity =
        wrapBackendExceptions(userRepository) { diseasesSource.setUserDiseases(diseasesDataEntity) }

    override fun reloadSetDiseasesUserRequest(diseasesDataEntity: DiseasesDataEntity) {
        diseasesSaveLazyFlowSubject.reloadArgument(diseasesDataEntity)
    }

}