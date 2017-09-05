package com.appspell.android.templates.mvi.list.model

import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import com.google.gson.GsonBuilder
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class ListInteractorImpl : ListInteractor {
    var viewState: ListViewState = ListViewState(true)

    override fun getLastViewState() = viewState

    override fun setLastViewState(viewState: ListViewState) {
        this.viewState = viewState
    }

    override fun requestList(): Observable<ListViewState> {
        val page = 1
        val user = "square" //@FIXME any user name for testing

        return useCache()
                .flatMap { savedViewState: ListViewState? ->
                    if (!validateCache(savedViewState, page, user)) {
                        return@flatMap apiCall(page, user)
                    } else {
                        return@flatMap Observable.just(savedViewState!!)
                    }
                }
                .subscribeOn(Schedulers.io())

    }

    private fun useCache(): Observable<ListViewState> {
        return Observable.just(viewState)
    }

    private fun apiCall(page: Int, user: String): Observable<ListViewState> {
        val api = getDataProvider().create(GitHubAPI::class.java)
        return api
                .getRepos(user, page, GitHubAPI.ITEMS_PER_PAGE)
                .flatMap { list: List<DataEntity> ->
                    val newState = ListViewState(false, list, page, user)
                    Observable.just(newState)
                }
                .startWith(ListViewState(true))
    }

    private fun validateCache(viewState: ListViewState?, page: Int, user: String): Boolean {
        return viewState != null && !viewState.list.isEmpty() && viewState.page == page && viewState.user == user
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

