package com.mhasancse15.cleanmaster.domain.util

import okhttp3.ResponseBody

data class BaseResource<out T>(
    val status: Status,
    val data: T?,
    val message: String?,
    val isNetworkError: Boolean? = null,
    val errorCode: Int? = null,
    val errorBody: ResponseBody? = null) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): BaseResource<T & Any> {
            return BaseResource(Status.SUCCESS, data, null)
        }

        fun <T> error(message: String, isNetworkError: Boolean? = null, errorCode: Int? = null, errorBody: ResponseBody? = null, data: T? = null): BaseResource<T> {
            return BaseResource(Status.ERROR, data, message, isNetworkError, errorCode, errorBody)
        }

        fun <T> loading(data: T? = null): BaseResource<T> {
            return BaseResource(Status.LOADING, data, null)
        }
    }



}