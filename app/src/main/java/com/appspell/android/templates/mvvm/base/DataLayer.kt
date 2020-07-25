package com.appspell.android.templates.mvvm.base

import android.accounts.NetworkErrorException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Call

class NetworkErrorException : Exception()

fun <DTO, DO> Call<DTO>.enqueueInBackground(
    background: suspend (DTO?) -> DO?,
    success: (DO?) -> Unit,
    error: (Throwable?) -> Unit,
    coroutineScope: CoroutineScope
) {
    coroutineScope.launch {
        try {
            // Retrofit. Synchronously send the request and return its response
            val response = withContext(Dispatchers.IO) { execute() }

            if (response.isSuccessful) {
                val obj = withContext(Dispatchers.Default) { background(response.body()) }
                success(obj)
            } else {
                error(NetworkErrorException())
            }
        } catch (ex: Exception) {
            error(ex)
        }
    }
}