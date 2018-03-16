package com.appspell.android.templates.mvi.list.model

import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GitHubAPI {
    @GET("/users/{user}/repos")
    fun getRepos(@Path("user") user: String, @Query("page") page: Int, @Query("per_page") perPage: Int): Observable<List<DataEntity>>

    companion object {
        const val ITEMS_PER_PAGE = 50
        const val BASE_URL = "https://api.github.com"
    }
}