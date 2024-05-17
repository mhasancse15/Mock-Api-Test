package com.mhasancse15.cleanmaster.data.mapper
import com.mhasancse15.cleanmaster.data.source.dto.movie.MovieDto
import com.mhasancse15.cleanmaster.domain.model.Movie

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