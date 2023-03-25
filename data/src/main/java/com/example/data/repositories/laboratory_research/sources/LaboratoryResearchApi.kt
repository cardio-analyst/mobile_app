package com.example.data.repositories.laboratory_research.sources

import com.example.data.repositories.laboratory_research.sources.entities.*
import retrofit2.http.*

interface LaboratoryResearchApi {

    @GET("analyses")
    suspend fun getLaboratoryResearches(): GetLaboratoryResearchArrayResponseEntity

    @POST("analyses")
    suspend fun createLaboratoryResearch(
        @Body createLaboratoryResearchRequestEntity: CreateLaboratoryResearchRequestEntity
    ): CreateLaboratoryResearchResponseEntity

    @PUT("analyses/{laboratoryResearchId}")
    suspend fun updateLaboratoryResearch(
        @Path("laboratoryResearchId") laboratoryResearchId: Long,
        @Body body: UpdateLaboratoryResearchRequestEntity
    ): UpdateLaboratoryResearchResponseEntity
}