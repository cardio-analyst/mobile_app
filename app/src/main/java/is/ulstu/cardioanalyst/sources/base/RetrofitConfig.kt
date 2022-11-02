package `is`.ulstu.cardioanalyst.sources.base

import com.squareup.moshi.Moshi
import retrofit2.Retrofit

/**
 * All stuffs required for making HTTP-requests with Retrofit client and Moshi
 * for parsing JSON-messages.
 */
class RetrofitConfig(
    val retrofit: Retrofit,
    val moshi: Moshi
)