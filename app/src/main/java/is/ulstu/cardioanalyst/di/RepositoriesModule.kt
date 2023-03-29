package `is`.ulstu.cardioanalyst.di

import com.example.authorization.domain.UserSignInRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import `is`.ulstu.cardioanalyst.glue.user.AdapterUserSignInRepository

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindUserSignInRepository(
        adapterUserSignInRepository: AdapterUserSignInRepository
    ): UserSignInRepository
}