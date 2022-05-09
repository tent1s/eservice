package com.example.testapplt.data.dataModule

import com.example.testapplt.data.remote.login.repository.LoginRepositoryImp
import com.example.testapplt.domain.repository.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
interface RepositoryModule {

    @Singleton
    @Binds
    fun loginRepository(loginRepositoryImp: LoginRepositoryImp): LoginRepository

}