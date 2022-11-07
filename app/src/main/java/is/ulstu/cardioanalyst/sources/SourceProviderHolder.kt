package `is`.ulstu.cardioanalyst.sources

import `is`.ulstu.cardioanalyst.app.Const
import `is`.ulstu.cardioanalyst.app.Singletons
import `is`.ulstu.cardioanalyst.sources.base.RetrofitConfig
import `is`.ulstu.cardioanalyst.sources.base.RetrofitSourcesProvider
import com.squareup.moshi.Moshi
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object SourceProviderHolder {

    val sourcesProvider: SourcesProvider by lazy {
        val moshi = Moshi.Builder().build()
        val config = RetrofitConfig(
            retrofit = createRetrofit(moshi),
            moshi = moshi
        )
        RetrofitSourcesProvider(config)
    }

    /**
     * Create an instance of Retrofit client.
     */
    private fun createRetrofit(moshi: Moshi): Retrofit {
        return Retrofit.Builder()
            .baseUrl(Const.BASE_URL)
            .client(createOkHttpClient())
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    /**
     * Create an instance of OkHttpClient with interceptors for authorization
     * @see [createAuthorizationInterceptor]
     */
    private fun createOkHttpClient(): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(
                createAuthorizationInterceptor()
            )
            .build()
    }

    /**
     * Add Authorization header to each request if JWT-token exists.
     */
    private fun createAuthorizationInterceptor(): Interceptor {
        return Interceptor { chain ->
            val newBuilder = chain.request().newBuilder()
            val token = Singletons.appSettings.getUserAccountAccessToken()
            if (token != null) {
                newBuilder.addHeader("Authorization", Const.BEARER_AUTH + token)
            }
            return@Interceptor chain.proceed(newBuilder.build())
        }
    }
}