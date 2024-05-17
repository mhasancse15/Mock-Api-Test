package com.mhasancse15.task.di

import com.mhasancse15.task.data.source.BookApiService
import com.mhasancse15.task.data.source.RemoteDateSourceImpl
import com.mhasancse15.task.domain.source.RemoteDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

    @Provides
    @Singleton
    fun provideRemoteDateSource(remoteService: BookApiService): RemoteDataSource =
        RemoteDateSourceImpl(remoteService)
}