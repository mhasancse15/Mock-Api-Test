package com.mhasancse15.cleanmaster.di

import com.mhasancse15.cleanmaster.data.repository.MovieRepositoryImpl
import com.mhasancse15.cleanmaster.data.source.MovieService
import com.mhasancse15.cleanmaster.domain.repository.MovieRepository
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
    fun provideMovieRepository(
        movieService: MovieService
    ): MovieRepository =
        MovieRepositoryImpl( movieService)
}