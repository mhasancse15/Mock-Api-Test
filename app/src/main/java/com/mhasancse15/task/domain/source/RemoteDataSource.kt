package com.mhasancse15.task.domain.source

import com.mhasancse15.task.data.model.BookListResponse

interface RemoteDataSource {
    suspend fun bookList(): BookListResponse
}