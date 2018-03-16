package com.appspell.android.templates.mvvm.list

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubService {
    @GET("/users/{user}/repos")
    fun getRepos(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") perPage: Int): Call<List<DataEntity>>

    companion object {
        const val ITEMS_PER_PAGE = 50
        const val BASE_URL = "https://api.github.com"
    }
}

data class DataEntity(
        val id: Long,
        val name: String,
        val description: String
)