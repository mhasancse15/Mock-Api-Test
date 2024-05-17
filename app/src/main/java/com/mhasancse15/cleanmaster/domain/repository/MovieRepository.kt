package com.mhasancse15.cleanmaster.domain.repository

import androidx.paging.PagingData
import com.mhasancse15.cleanmaster.domain.model.Movie
import kotlinx.coroutines.flow.Flow

interface MovieRepository {
    fun getMovieList(apiKey: String, language: String): Flow<PagingData<Movie>>
}