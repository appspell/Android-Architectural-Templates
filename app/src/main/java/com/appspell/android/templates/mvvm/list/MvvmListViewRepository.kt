package com.appspell.android.templates.mvvm.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.appspell.android.templates.mvi.list.model.GitHubAPI
import com.appspell.android.templates.mvvm.base.request
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

private const val FIRST_PAGE = 1

abstract class MvvmListViewRepository {

    abstract val items: LiveData<List<DataEntity>>
    abstract val errors: LiveData<Throwable>

    var page: Int = FIRST_PAGE

    abstract fun fetch(user: String)
    abstract fun retry()
    abstract fun loadNextPage()

    //@TODO use DI
    internal fun getDataProvider(): Retrofit {
        val gson = GsonBuilder().create()

        return Retrofit.Builder()
                .baseUrl(GitHubAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
    }
}

class MvvmListViewRepositoryImpl : MvvmListViewRepository() {

    override val items = MutableLiveData<List<DataEntity>>()
    override val errors = MutableLiveData<Throwable>()

    private var lastUsedUser: String? = null

    override fun fetch(user: String) {
        lastUsedUser = user
        getDataProvider().create(GitHubService::class.java)
                .getRepos(user, page, GitHubAPI.ITEMS_PER_PAGE)
                .request(success = { _, obj -> items.value = obj },
                        error = { errors.value = it })
    }

    override fun retry() {
        lastUsedUser?.let { user ->
            page = FIRST_PAGE
            fetch(user)
        }
    }

    override fun loadNextPage() {
        page++
        fetch(lastUsedUser!!)
    }
}