package com.example.data.repositories.treatment_adherence_test.sources

import com.example.data.base.network.BaseRetrofitSource
import com.example.data.base.network.RetrofitConfig
import com.example.data.repositories.treatment_adherence_test.sources.entities.TreatmentAdherenceDataEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RetrofitTreatmentAdherenceTestSource @Inject constructor(
    config: RetrofitConfig
) : BaseRetrofitSource(config), TreatmentAdherenceTestSource {
    private val treatmentAdherenceTestApi = retrofit.create(TreatmentAdherenceTestApi::class.java)

    override suspend fun getUserTreatmentAdherence(): TreatmentAdherenceDataEntity =
        wrapRetrofitExceptions { treatmentAdherenceTestApi.getUserTreatmentAdherence() }

    override suspend fun setUserTreatmentAdherence(treatmentAdherenceDataEntity: TreatmentAdherenceDataEntity): TreatmentAdherenceDataEntity =
        wrapRetrofitExceptions { treatmentAdherenceTestApi.setUserTreatmentAdherence(treatmentAdherenceDataEntity) }
}