package com.example.data.repositories.laboratory_research.sources

import com.example.data.repositories.laboratory_research.sources.entities.*
import retrofit2.http.*

interface LaboratoryResearchApi {

    @GET("analyses")
    suspend fun getLaboratoryResearches(): GetLaboratoryResearchArrayResponseDataEntity

    @POST("analyses")
    suspend fun createLaboratoryResearch(
        @Body createLaboratoryResearchRequestDataEntity: CreateLaboratoryResearchRequestDataEntity
    ): CreateLaboratoryResearchResponseDataEntity

    @PUT("analyses/{laboratoryResearchId}")
    suspend fun updateLaboratoryResearch(
        @Path("laboratoryResearchId") laboratoryResearchId: Long,
        @Body body: UpdateLaboratoryResearchRequestDataEntity
    ): UpdateLaboratoryResearchResponseDataEntity
}