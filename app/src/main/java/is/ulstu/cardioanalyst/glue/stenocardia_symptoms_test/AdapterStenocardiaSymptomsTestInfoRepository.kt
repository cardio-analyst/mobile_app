package `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test

import com.example.common.flows.ResultState
import com.example.data.repositories.stenocardia_symptoms_test.StenocardiaSymptomsTestDataDataRepository
import com.example.questionnaires_list.domain.StenocardiaSymptomsTestInfoRepository
import com.example.questionnaires_list.domain.entities.StenocardiaSymptoms
import `is`.ulstu.cardioanalyst.glue.stenocardia_symptoms_test.mappers.toStenocardiaSymptoms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AdapterStenocardiaSymptomsTestInfoRepository @Inject constructor(
    private val stenocardiaSymptomsRepository: StenocardiaSymptomsTestDataDataRepository
) : StenocardiaSymptomsTestInfoRepository {

    override fun getUserStenocardiaSymptoms(): Flow<ResultState<StenocardiaSymptoms>> =
        stenocardiaSymptomsRepository.getUserStenocardiaSymptoms().map { resultState ->
            resultState.map { stenocardiaSymptomsDataEntity ->
                stenocardiaSymptomsDataEntity.toStenocardiaSymptoms()
            }
        }

    override fun removeAllListenersGet() = stenocardiaSymptomsRepository.removeAllListenersGet()

    override fun reloadGetUserStenocardiaSymptoms() =
        stenocardiaSymptomsRepository.reloadGetUserStenocardiaSymptoms()

}