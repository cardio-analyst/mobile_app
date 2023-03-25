package com.example.data.di

import com.example.data.repositories.basic_indicators.BasicIndicatorsDataRepository
import com.example.data.repositories.basic_indicators.IBasicIndicatorsDataRepository
import com.example.data.repositories.diseases.DiseasesDataRepository
import com.example.data.repositories.diseases.IDiseasesDataRepository
import com.example.data.repositories.laboratory_research.ILaboratoryResearchDataRepository
import com.example.data.repositories.laboratory_research.LaboratoryResearchDataRepository
import com.example.data.repositories.lifestyle.ILifestyleDataRepository
import com.example.data.repositories.lifestyle.LifestyleDataRepository
import com.example.data.repositories.recommendations.IRecommendationsDataRepository
import com.example.data.repositories.recommendations.RecommendationsDataRepository
import com.example.data.repositories.users.IUserDataRepository
import com.example.data.repositories.users.UserDataRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindBasicIndicatorsRepository(
        basicIndicatorsDBRepository: BasicIndicatorsDataRepository
    ): IBasicIndicatorsDataRepository

    @Binds
    abstract fun bindDiseasesRepository(
        diseasesDataRepository: DiseasesDataRepository
    ): IDiseasesDataRepository

    @Binds
    abstract fun bindLaboratoryResearchRepository(
        laboratoryResearchDBRepository: LaboratoryResearchDataRepository
    ): ILaboratoryResearchDataRepository

    @Binds
    abstract fun bindLifestyleRepository(
        lifestyleDBRepository: LifestyleDataRepository
    ): ILifestyleDataRepository

    @Binds
    abstract fun bindRecommendationsRepository(
        recommendationsDBRepository: RecommendationsDataRepository
    ): IRecommendationsDataRepository

    @Binds
    abstract fun bindUserRepository(
        userDBRepository: UserDataRepository
    ): IUserDataRepository

}