package com.mhasancse15.task.data.source

import com.mhasancse15.task.common.constants.AppConstant.BOOK_LIST_PATH
import com.mhasancse15.task.data.model.BookListResponse
import retrofit2.http.GET

interface BookApiService {

    @GET(BOOK_LIST_PATH)
    suspend fun getBookList(): BookListResponse
}