package com.example.data.base.network

import com.squareup.moshi.Moshi
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * All stuffs required for making HTTP-requests with Retrofit client and Moshi
 * for parsing JSON-messages.
 */
@Singleton
class RetrofitConfig @Inject constructor(
    val retrofit: Retrofit,
    val moshi: Moshi
)