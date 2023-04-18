package com.example.data.repositories.stenocardia_symptoms_test

import com.example.common.flows.LazyFlowSubject
import com.example.common.flows.ResultState
import com.example.data.repositories.stenocardia_symptoms_test.sources.StenocardiaSymptomsTestSource
import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import com.example.data.repositories.users.IUserDataRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StenocardiaSymptomsTestDataRepository @Inject constructor(
    private val stenocardiaSymptomsTestSource: StenocardiaSymptomsTestSource,
    private val userRepository: IUserDataRepository,
) : IStenocardiaSymptomsTestDataRepository {

    private val stenocardiaSymptomsLazyFlowSubject =
        LazyFlowSubject<Unit, StenocardiaSymptomsDataEntity> {
            doGetUserStenocardiaSymptoms()
        }

    private val stenocardiaSymptomsSaveLazyFlowSubject =
        LazyFlowSubject<StenocardiaSymptomsDataEntity, StenocardiaSymptomsDataEntity> { stenocardiaSymptomsDataEntity ->
            doSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity)
        }


    override fun getUserStenocardiaSymptoms(): Flow<ResultState<StenocardiaSymptomsDataEntity>> =
        stenocardiaSymptomsLazyFlowSubject.listen(Unit)

    private suspend fun doGetUserStenocardiaSymptoms(): StenocardiaSymptomsDataEntity =
        wrapBackendExceptions(userRepository) {
            stenocardiaSymptomsTestSource.getUserStenocardiaSymptoms()
        }

    override fun setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity): Flow<ResultState<StenocardiaSymptomsDataEntity>> =
        stenocardiaSymptomsSaveLazyFlowSubject.listen(stenocardiaSymptomsDataEntity)

    private suspend fun doSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity) =
        wrapBackendExceptions(userRepository) {
            stenocardiaSymptomsTestSource.setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity)
        }

    override fun reloadGetUserStenocardiaSymptoms() =
        stenocardiaSymptomsLazyFlowSubject.reloadAll()

    override fun reloadSetUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity) =
        stenocardiaSymptomsSaveLazyFlowSubject.reloadArgument(stenocardiaSymptomsDataEntity)
}