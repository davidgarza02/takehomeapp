package com.davidgarza.takehome.di

import com.davidgarza.takehome.api.ApiInterface
import com.davidgarza.takehome.repository.RemoteRepository
import com.davidgarza.takehome.repository.RemoteRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRemoteRepository(
        apiInterface: ApiInterface
    ): RemoteRepository = RemoteRepositoryImpl(apiInterface)
}