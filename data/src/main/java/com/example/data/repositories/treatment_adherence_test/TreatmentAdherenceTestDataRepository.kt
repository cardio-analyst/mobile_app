package com.example.data.repositories.treatment_adherence_test

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.treatment_adherence_test.sources.TreatmentAdherenceTestSource
import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TreatmentAdherenceTestDataRepository @Inject constructor(
    private val treatmentAdherenceTestSource: TreatmentAdherenceTestSource,
    private val userRepository: IUserDataRepository,
) : ITreatmentAdherenceTestDataRepository {

    private val treatmentAdherenceLazyFlowSubject =
        LazyFlowSubject<Unit, TreatmentAdherenceDataEntity> {
            doGetUserTreatmentAdherence()
        }

    private val treatmentAdherenceSaveLazyFlowSubject =
        LazyFlowSubject<TreatmentAdherenceDataEntity, TreatmentAdherenceDataEntity> { treatmentAdherenceDataEntity ->
            doSetUserTreatmentAdherence(treatmentAdherenceDataEntity)
        }


    override fun getUserTreatmentAdherence(): Flow<ResultState<TreatmentAdherenceDataEntity>> =
        treatmentAdherenceLazyFlowSubject.listen(Unit)

    private suspend fun doGetUserTreatmentAdherence(): TreatmentAdherenceDataEntity =
        wrapBackendExceptions(userRepository) {
            treatmentAdherenceTestSource.getUserTreatmentAdherence()
        }

    override fun setUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity): Flow<ResultState<TreatmentAdherenceDataEntity>> =
        treatmentAdherenceSaveLazyFlowSubject.listen(treatmentAdherenceDataEntity)

    private suspend fun doSetUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity) =
        wrapBackendExceptions(userRepository) {
            treatmentAdherenceTestSource.setUserTreatmentAdherence(treatmentAdherenceDataEntity)
        }

    override fun reloadGetUserTreatmentAdherence() =
        treatmentAdherenceLazyFlowSubject.reloadAll()

    override fun reloadSetUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity) =
        treatmentAdherenceSaveLazyFlowSubject.reloadArgument(treatmentAdherenceDataEntity)
}