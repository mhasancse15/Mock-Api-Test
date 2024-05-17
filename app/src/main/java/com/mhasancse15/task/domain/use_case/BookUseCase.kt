package com.mhasancse15.task.domain.use_case

import com.mhasancse15.task.domain.repository.BookRepository
import javax.inject.Inject

class BookUseCase @Inject constructor(private val repository: BookRepository) {
    operator fun invoke() = repository.bookList()
}