package com.mhasancse15.task.common.utils

sealed class Resource<out T : Any> {
    data object Loading : Resource<Nothing>()
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val throwable: Throwable) : Resource<Nothing>()
}