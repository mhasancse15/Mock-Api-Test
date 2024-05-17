package com.mhasancse15.cleanmaster.data.source

import com.mhasancse15.cleanmaster.data.source.dto.movieDetails.MovieDetailsResponseDto
import com.mhasancse15.cleanmaster.domain.model.MovieDetailsResponse
import com.mhasancse15.cleanmaster.domain.source.RemoteDataSource
import javax.inject.Inject

class RemoteDateSourceImpl @Inject constructor(private val remoteService: MovieService) :
    RemoteDataSource {
    override suspend fun getMovieDetails(movieId: String, apiKey: String): MovieDetailsResponseDto {
        return remoteService.getMovieDetails(movieId, apiKey)
    }
}