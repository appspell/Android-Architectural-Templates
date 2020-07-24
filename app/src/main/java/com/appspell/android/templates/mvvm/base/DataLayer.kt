package com.appspell.android.templates.mvvm.base

import android.accounts.NetworkErrorException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NetworkErrorException : Exception()

fun <T> Call<T>.enqueueWithResult(
    success: (T?) -> Unit,
    error: (Throwable?) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            when (response?.code()) {
                in 200..399 -> success.invoke(response?.body())
                else -> error.invoke(NetworkErrorException())
            }
        }

        override fun onFailure(call: Call<T>?, throwable: Throwable?) {
            error.invoke(throwable)
        }
    })
}
