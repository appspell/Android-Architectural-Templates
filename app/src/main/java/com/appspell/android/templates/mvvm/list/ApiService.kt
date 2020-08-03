package com.appspell.android.templates.mvvm.list

import retrofit2.http.GET

interface ApiService {
    @GET("https://run.mocky.io/v3/c9850641-caf4-49da-b309-72d1e0fdf3c4/?mocky-delay=1000ms")
    suspend fun fetchList(): List<ItemDTO>

    companion object {
        const val BASE_URL = "https://api.github.com"
    }
}

data class ItemDTO(
    val id: String,
    val name: String,
    val description: String
)