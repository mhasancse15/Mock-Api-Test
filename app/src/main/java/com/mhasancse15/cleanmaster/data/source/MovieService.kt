package com.mhasancse15.cleanmaster.data.source

import com.mhasancse15.cleanmaster.data.source.dto.movie.MovieResultDto
import retrofit2.http.GET
import retrofit2.http.Query

interface MovieService {

    @GET("3/movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("api_key") apiKey: String,
        @Query("language") language: String,
        @Query("page") page: Int
    ): MovieResultDto
}