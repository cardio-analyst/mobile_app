package com.example.data.repositories.lifestyle.sources

import com.example.data.repositories.lifestyle.sources.entities.LifestyleDataEntity
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface LifestyleApi {
    @GET("lifestyles/info")
    suspend fun getUserLifestyle(): LifestyleDataEntity

    @PUT("lifestyles/edit")
    suspend fun setUserLifestyle(@Body body: LifestyleDataEntity): LifestyleDataEntity
}