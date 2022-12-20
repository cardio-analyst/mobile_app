package `is`.ulstu.cardioanalyst.di

import `is`.ulstu.cardioanalyst.models.basic_indicators.BasicIndicatorsDBRepository
import `is`.ulstu.cardioanalyst.models.basic_indicators.IBasicIndicatorsRepository
import `is`.ulstu.cardioanalyst.models.diseases.DiseasesDBRepository
import `is`.ulstu.cardioanalyst.models.diseases.IDiseasesRepository
import `is`.ulstu.cardioanalyst.models.laboratory_research.ILaboratoryResearchRepository
import `is`.ulstu.cardioanalyst.models.laboratory_research.LaboratoryResearchDBRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.ILifestyleRepository
import `is`.ulstu.cardioanalyst.models.lifestyle.LifestyleDBRepository
import `is`.ulstu.cardioanalyst.models.recommendations.IRecommendationsRepository
import `is`.ulstu.cardioanalyst.models.recommendations.RecommendationsDBRepository
import `is`.ulstu.cardioanalyst.models.users.IUserRepository
import `is`.ulstu.cardioanalyst.models.users.UserDBRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindBasicIndicatorsRepository(
        basicIndicatorsDBRepository: BasicIndicatorsDBRepository
    ): IBasicIndicatorsRepository

    @Binds
    abstract fun bindDiseasesRepository(
        diseasesDBRepository: DiseasesDBRepository
    ): IDiseasesRepository

    @Binds
    abstract fun bindLaboratoryResearchRepository(
        laboratoryResearchDBRepository: LaboratoryResearchDBRepository
    ): ILaboratoryResearchRepository

    @Binds
    abstract fun bindLifestyleRepository(
        lifestyleDBRepository: LifestyleDBRepository
    ): ILifestyleRepository

    @Binds
    abstract fun bindRecommendationsRepository(
        recommendationsDBRepository: RecommendationsDBRepository
    ): IRecommendationsRepository

    @Binds
    abstract fun bindUserRepository(
        userDBRepository: UserDBRepository
    ): IUserRepository

}