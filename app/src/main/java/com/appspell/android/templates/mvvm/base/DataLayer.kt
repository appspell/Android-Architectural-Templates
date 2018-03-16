package com.appspell.android.templates.mvvm.base

import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

fun <T> Call<T>.request(success: (Int?, T?) -> Unit,
                        error: (Throwable?) -> Unit) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>?, response: Response<T>?) {
            success.invoke(response?.code(), response?.body())
        }

        override fun onFailure(call: Call<T>?, throwable: Throwable?) {
            error.invoke(throwable)
        }
    })
}
