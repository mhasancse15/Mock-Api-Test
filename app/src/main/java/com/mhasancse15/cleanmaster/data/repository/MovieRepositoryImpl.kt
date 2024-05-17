package com.mhasancse15.cleanmaster.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.mhasancse15.cleanmaster.common.constants.AppConstant
import com.mhasancse15.cleanmaster.data.paging.MoviesPagingSource
import com.mhasancse15.cleanmaster.data.source.MovieService
import com.mhasancse15.cleanmaster.domain.model.Movie
import com.mhasancse15.cleanmaster.domain.repository.MovieRepository
import kotlinx.coroutines.flow.Flow

class MovieRepositoryImpl(
    private val apiService: MovieService
) : MovieRepository {

    override fun getMovieList(apiKey: String, language: String): Flow<PagingData<Movie>> {
        return Pager(
            config = PagingConfig(
                pageSize = AppConstant.STARTING_PAGE_INDEX,
                enablePlaceholders = false
            ),
            pagingSourceFactory = { MoviesPagingSource(apiService, apiKey, language) }
        ).flow
    }
}