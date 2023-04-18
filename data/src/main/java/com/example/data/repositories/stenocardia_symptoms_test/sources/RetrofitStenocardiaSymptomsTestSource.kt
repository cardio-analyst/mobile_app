package com.example.data.repositories.stenocardia_symptoms_test.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.stenocardia_symptoms_test.sources.entities.StenocardiaSymptomsDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitStenocardiaSymptomsTestSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), StenocardiaSymptomsTestSource {

    private val stenocardiaSymptomsTestApi = retrofit.create(StenocardiaSymptomsTestApi::class.java)

    override suspend fun getUserStenocardiaSymptoms(): StenocardiaSymptomsDataEntity =
        wrapRetrofitExceptions { stenocardiaSymptomsTestApi.getUserStenocardiaSymptoms() }

    override suspend fun setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity: StenocardiaSymptomsDataEntity): StenocardiaSymptomsDataEntity =
        wrapRetrofitExceptions { stenocardiaSymptomsTestApi.setUserStenocardiaSymptoms(stenocardiaSymptomsDataEntity) }

}