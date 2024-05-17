package com.mhasancse15.cleanmaster.data.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mhasancse15.cleanmaster.data.mapper.toDomain
import com.mhasancse15.cleanmaster.common.constants.AppConstant
import com.mhasancse15.cleanmaster.data.source.MovieService
import com.mhasancse15.cleanmaster.domain.model.Movie

class MoviesPagingSource(
    private val apiService: MovieService,
    private val apiKey: String,
    private val language: String
) : PagingSource<Int, Movie>() {

    override fun getRefreshKey(state: PagingState<Int, Movie>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Movie> {
        val position = params.key ?: AppConstant.STARTING_PAGE_INDEX
        return try {
            val response =
                apiService.getTopRatedMovies(
                    apiKey,
                    language,
                    position
                )
            val movieList = response.results
            LoadResult.Page(
                data = movieList!!.map { it.toDomain() },
                prevKey = if (position == AppConstant.STARTING_PAGE_INDEX) null else position - AppConstant.DEFAULT_LIMIT,
                nextKey = if (movieList.isEmpty()) null else (position) + AppConstant.DEFAULT_LIMIT
            )

        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}