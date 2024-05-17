package com.mhasancse15.cleanmaster.domain.source

import com.mhasancse15.cleanmaster.data.source.dto.movieDetails.MovieDetailsResponseDto
import com.mhasancse15.cleanmaster.domain.model.MovieDetailsResponse

interface RemoteDataSource {
    suspend fun getMovieDetails(movieId: String, apiKey: String): MovieDetailsResponseDto
}