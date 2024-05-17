package com.mhasancse15.task.domain.repository


import com.mhasancse15.task.common.utils.Resource
import com.mhasancse15.task.data.model.BookListResponse
import kotlinx.coroutines.flow.Flow

interface BookRepository {
    fun bookList(): Flow<Resource<BookListResponse>>
}