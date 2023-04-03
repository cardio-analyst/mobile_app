package `is`.ulstu.cardioanalyst.di

import com.example.authorization.domain.UserSignInRepository
import com.example.diseases.domain.DiseasesRepository
import com.example.profile.domain.UserInfoRepository
import com.example.recommendations.domain.RecommendationsRepository
import com.example.registration.domain.UserSignUpRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `is`.ulstu.cardioanalyst.glue.diseases.AdapterDiseasesRepository
import `is`.ulstu.cardioanalyst.glue.recommendations.AdapterRecommendationsRepository
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserInfoRepository
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserSignInRepository
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserSignUpRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindUserSignInRepository(
        adapterUserSignInRepository: AdapterUserSignInRepository
    ): UserSignInRepository

    @Binds
    abstract fun bindUserSignUpRepository(
        adapterUserSignUpRepository: AdapterUserSignUpRepository
    ): UserSignUpRepository

    @Binds
    abstract fun bindUserInfoRepository(
        adapterUserInfoRepository: AdapterUserInfoRepository
    ): UserInfoRepository

    @Binds
    abstract fun bindRecommendationsRepository(
        adapterRecommendationsRepository: AdapterRecommendationsRepository
    ): RecommendationsRepository

    @Binds
    abstract fun bindDiseasesRepository(
        adapterDiseasesRepository: AdapterDiseasesRepository
    ): DiseasesRepository
}