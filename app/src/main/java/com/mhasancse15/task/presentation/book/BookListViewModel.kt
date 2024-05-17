package com.mhasancse15.task.presentation.book

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mhasancse15.task.common.utils.Resource
import com.mhasancse15.task.data.model.BookListResponse
import com.mhasancse15.task.domain.use_case.BookUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookListViewModel @Inject constructor(private val bookUseCase: BookUseCase) : ViewModel() {

    private val _state = MutableStateFlow<Resource<BookListResponse>?>(null)
    val state = _state.asStateFlow()

    fun getBookList() = viewModelScope.launch {

        bookUseCase().collect {
            _state.emit(it)
        }
    }

}