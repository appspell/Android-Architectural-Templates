package com.appspell.android.templates.mvvm.base

import android.accounts.NetworkErrorException
import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call
import java.io.IOException

class NetworkErrorException : Exception()

fun <DTO, DO> Call<DTO>.enqueueInBackground(
    background: suspend (DTO?) -> DO?,
    success: (DO?) -> Unit,
    error: (Throwable?) -> Unit,
    coroutineScope: CoroutineScope
) {
    Log.i("COR", "`enqueueInBackground` in thread ${Thread.currentThread().name}")
    try {
        coroutineScope.launch {
            Log.i("COR", "`isSuccessful` in thread ${Thread.currentThread().name}")
            // Retrofit. Synchronously send the request and return its response
            val response = withContext(Dispatchers.IO) {
                Log.i("COR", "`execute` in thread ${Thread.currentThread().name}")
                execute()
            }

            if (response.isSuccessful) {
                Log.i("COR", "`coroutineScope` in thread ${Thread.currentThread().name}")
                val obj = withContext(Dispatchers.Default) {
                    Log.i("COR", "`coroutineScope` in thread ${Thread.currentThread().name}")
                    background(response.body())
                }

                Log.i("COR", "`withContext` in thread ${Thread.currentThread().name}")
                success.invoke(obj)
            } else {
                Log.i("COR", "`else` in thread ${Thread.currentThread().name}")
                error.invoke(NetworkErrorException())
            }
        }
    } catch (ex: IOException) {
        error.invoke(ex)
    }
}

