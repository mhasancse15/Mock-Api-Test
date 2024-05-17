package com.mhasancse15.task.data.repository

import com.mhasancse15.task.common.utils.Resource
import com.mhasancse15.task.domain.repository.BookRepository
import com.mhasancse15.task.domain.source.RemoteDataSource
import kotlinx.coroutines.flow.flow

class BookRepositoryImpl(
    private val remoteDataSource: RemoteDataSource
) : BookRepository {

    override fun bookList() = flow {
        emit(Resource.Loading)
        try {
            val response = remoteDataSource.bookList()
            emit(Resource.Success(response))
        } catch (t: Throwable) {
            emit(Resource.Error(t))
        }
    }
}
