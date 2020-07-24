package com.appspell.android.templates.mvvm.list

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("/users/{user}/repos")
    fun fetchList(@Path("user") user: String): Call<List<ItemDTO>>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}

data class ItemDTO(
    val id: Long,
    val name: String,
    val description: String
)