package `is`.ulstu.cardioanalyst.glue.treatment_adherence_test

import com.example.common.flows.ResultState
import com.example.data.repositories.treatment_adherence_test.TreatmentAdherenceTestDataRepository
import com.example.questionnaires_list.domain.TreatmentAdherenceTestInfoRepository
import com.example.questionnaires_list.domain.entities.TreatmentAdherence
import `is`.ulstu.cardioanalyst.glue.treatment_adherence_test.mappers.toTreatmentAdherence
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterTreatmentAdherenceTestInfoRepository @Inject constructor(
    private val treatmentAdherenceTestDataRepository: TreatmentAdherenceTestDataRepository,
) : TreatmentAdherenceTestInfoRepository {

    override fun getUserTreatmentAdherence(): Flow<ResultState<TreatmentAdherence>> =
        treatmentAdherenceTestDataRepository.getUserTreatmentAdherence().map { resultState ->
            resultState.map { treatmentAdherenceDataEntity ->
                treatmentAdherenceDataEntity.toTreatmentAdherence()
            }
        }

    override fun reloadGetUserTreatmentAdherence() =
        treatmentAdherenceTestDataRepository.reloadGetUserTreatmentAdherence()

}