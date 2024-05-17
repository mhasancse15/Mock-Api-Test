package com.mhasancse15.cleanmaster.domain.util

import android.util.Log
import com.google.gson.JsonSyntaxException
import om.mhasancse15.cleanmaster.R
import retrofit2.HttpException
import retrofit2.Response
import java.net.UnknownHostException

abstract class BaseDataSource {

    companion object {
        private const val TAG = "BaseDataSource"
    }

    protected suspend fun <T> getResult(call: suspend () -> Response<T>): BaseResource<T> {
        try {
            val response = call()
            if (response.isSuccessful) {
                Log.d(TAG, "getResult: isSuccessful true")
                val body = response.body()
                if (body != null) {
                    Log.d(TAG, "getResult: body not null")
                    return BaseResource.success(body)
                }
            }
            Log.d(TAG, "getResult: outside")


            return if (NetworkTracker.hasNetwork(AppContextHolder.context)){
                val errorMessage = response.errorBody()
                BaseResource.error(" $errorMessage", data = response.body())
            }else{
                BaseResource.error(AppContextHolder.context.getString(R.string.no_internet_message), data = response.body())
            }


        } catch (throwable: Throwable) {
            Log.d(TAG, "getResult: inside catch $throwable")
            return when (throwable) {
                is HttpException -> {
                    BaseResource.error(
                        throwable.message ?: throwable.toString(),
                        false,
                        throwable.code(),
                        throwable.response()?.errorBody()
                    )
                }
                is UnknownHostException -> {
                    BaseResource.error(throwable.message ?: throwable.toString(), true)
                }

                is JsonSyntaxException -> {
                    BaseResource.error("Internal Server Error", data = null)
                }

                is IllegalStateException -> {
                    BaseResource.error("Internal Server Error", data = null)
                }

                else -> {
                    Log.d(TAG, "getResult: inside catch $throwable")
                    BaseResource.error(throwable.message ?: throwable.toString(), true)
                }
            }
        }
    }

}