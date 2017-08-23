package com.appspell.android.templates.mvi.list.model

import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ListInteractorImpl : ListInteractor {
    override fun requestList(): Observable<List<DataEntity>>? {
        val page = 1
        val user = "square" //@FIXME any user name for testing

        val api = getDataProvider().create(GitHubAPI::class.java)
        return api.getRepos(user, page, GitHubAPI.ITEMS_PER_PAGE)
                .subscribeOn(Schedulers.io())
    }

    //@TODO use DI
    private fun getDataProvider(): Retrofit {
        val gson = GsonBuilder().create()

        return Retrofit.Builder()
                .baseUrl(GitHubAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}