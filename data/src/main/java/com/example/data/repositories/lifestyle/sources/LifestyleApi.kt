package com.example.data.repositories.lifestyle.sources

import com.example.data.repositories.lifestyle.sources.entities.LifestyleMainEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface LifestyleApi {
    @GET("lifestyles/info")
    suspend fun getUserLifestyle(): LifestyleMainEntity

    @PUT("lifestyles/edit")
    suspend fun setUserLifestyle(@Body body: LifestyleMainEntity): LifestyleMainEntity
}