package com.mhasancse15.task.di

import com.mhasancse15.task.data.repository.BookRepositoryImpl
import com.mhasancse15.task.domain.repository.BookRepository
import com.mhasancse15.task.domain.source.RemoteDataSource
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
    fun provideDallERepository(
        remoteDataSource: RemoteDataSource
    ): BookRepository =
        BookRepositoryImpl(remoteDataSource)
}