package com.mhasancse15.task.data.source

import com.mhasancse15.task.data.model.BookListResponse
import com.mhasancse15.task.domain.source.RemoteDataSource

class RemoteDateSourceImpl (private val remoteService: BookApiService) : RemoteDataSource {

    override suspend fun bookList(): BookListResponse {
        return remoteService.getBookList()
    }
}