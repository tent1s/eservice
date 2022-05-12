package ru.omsu.eservice.data.dataModule

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.omsu.eservice.data.remote.login.repository.EducationCardRepositoryImp
import ru.omsu.eservice.data.remote.login.repository.LoginRepositoryImp
import ru.omsu.eservice.domain.repository.EducationCardRepository
import ru.omsu.eservice.domain.repository.LoginRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun loginRepository(loginRepositoryImp: LoginRepositoryImp): LoginRepository

    @Singleton
    @Binds
    fun educationCardRepository(educationCardRepositoryImp: EducationCardRepositoryImp): EducationCardRepository

}