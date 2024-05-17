package com.mhasancse15.task.data.model

data class BookListResponse (
    val status: Long? = null,
    val message: String? = null,
    val data: List<Book>? = null
)