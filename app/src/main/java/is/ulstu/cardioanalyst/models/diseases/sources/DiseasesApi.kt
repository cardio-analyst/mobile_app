package `is`.ulstu.cardioanalyst.models.diseases.sources

import `is`.ulstu.cardioanalyst.models.diseases.sources.entities.DiseasesMainEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface DiseasesApi {
    @GET("diseases/info")
    suspend fun getUserDiseases(): DiseasesMainEntity

    @PUT("diseases/edit")
    suspend fun setUserDiseases(@Body body: DiseasesMainEntity): DiseasesMainEntity
}