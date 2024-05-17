package com.mhasancse15.cleanmaster.data.mapper

import com.mhasancse15.cleanmaster.data.source.dto.movie.MovieDto
import com.mhasancse15.cleanmaster.data.source.dto.movieDetails.MovieDetailsResponseDto
import com.mhasancse15.cleanmaster.domain.model.Movie
import com.mhasancse15.cleanmaster.domain.model.MovieDetailsResponse

fun MovieDto.toDomain(): Movie {
    return Movie(
        adult = adult,
        backdropPath = backdropPath,
        id = id,
        originalLanguage = originalLanguage,
        originalTitle = originalTitle,
        overview = overview,
        popularity = popularity,
        posterPath = posterPath,
        releaseDate = releaseDate,
        title = title,
        video = video,
        voteAverage = voteAverage,
        voteCount = voteCount
    )
}

fun MovieDetailsResponseDto.toDomain(): MovieDetailsResponse {
    return MovieDetailsResponse(
        adult = adult,
        backdrop_path = backdrop_path,
        belongs_to_collection = belongs_to_collection,
        budget = budget,
        homepage = homepage
    )
}