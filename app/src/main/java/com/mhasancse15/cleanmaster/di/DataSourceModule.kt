package com.mhasancse15.cleanmaster.di


import com.mhasancse15.cleanmaster.data.source.MovieService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataSourceModule {

  /*  @Provides
    @Singleton
    fun provideRemoteDateSource(remoteService: MovieService): RemoteDataSource =
        RemoteDateSourceImpl(remoteService)*/
}