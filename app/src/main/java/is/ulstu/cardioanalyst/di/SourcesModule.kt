package `is`.ulstu.cardioanalyst.di

import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.BasicIndicatorsSource
import `is`.ulstu.cardioanalyst.models.basic_indicators.sources.RetrofitBasicIndicatorsSource
import `is`.ulstu.cardioanalyst.models.diseases.sources.DiseasesSource
import `is`.ulstu.cardioanalyst.models.diseases.sources.RetrofitDiseasesSource
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.LaboratoryResearchSource
import `is`.ulstu.cardioanalyst.models.laboratory_research.sources.RetrofitLaboratoryResearchSource
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.LifestyleSource
import `is`.ulstu.cardioanalyst.models.lifestyle.sources.RetrofitLifestyleSource
import `is`.ulstu.cardioanalyst.models.recommendations.sources.RecommendationsSource
import `is`.ulstu.cardioanalyst.models.recommendations.sources.RetrofitRecommendationsSource
import `is`.ulstu.cardioanalyst.models.users.sources.RetrofitUsersSource
import `is`.ulstu.cardioanalyst.models.users.sources.UsersSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class SourcesModule {

    @Binds
    abstract fun bindBasicIndicatorsSource(
        retrofitBasicIndicatorsSource: RetrofitBasicIndicatorsSource
    ): BasicIndicatorsSource

    @Binds
    abstract fun bindRetrofitDiseasesSource(
        retrofitDiseasesSource: RetrofitDiseasesSource
    ): DiseasesSource

    @Binds
    abstract fun bindRetrofitLaboratoryResearchSource(
        retrofitLaboratoryResearchSource: RetrofitLaboratoryResearchSource
    ): LaboratoryResearchSource

    @Binds
    abstract fun bindRetrofitLifestyleSource(
        retrofitLifestyleSource: RetrofitLifestyleSource
    ): LifestyleSource

    @Binds
    abstract fun bindRetrofitRecommendationsSource(
        retrofitRecommendationsSource: RetrofitRecommendationsSource
    ): RecommendationsSource

    @Binds
    abstract fun bindRetrofitUsersSource(
        retrofitUsersSource: RetrofitUsersSource
    ): UsersSource

}