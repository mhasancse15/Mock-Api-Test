package com.mhasancse15.cleanmaster.presentation.movieList

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.mhasancse15.cleanmaster.common.baseComponent.BaseViewModel
import com.mhasancse15.cleanmaster.common.constants.AppConstant
import com.mhasancse15.cleanmaster.domain.model.Movie
import com.mhasancse15.cleanmaster.domain.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieListViewModel @Inject constructor(private val movieRepository: MovieRepository) : BaseViewModel() {
    var wrapperType: String = AppConstant.DEFAULT_WRAPPER_TYPE
    private val _movieList: MutableLiveData<PagingData<Movie>> = MutableLiveData(PagingData.empty())
    val movieList = _movieList
    fun getMovieList(apiKey: String, language: String) {
        viewModelScope.launch {
            movieRepository.getMovieList(apiKey, language).distinctUntilChanged()
                .collectLatest {
                    _movieList.value = it
                }
        }
    }
}