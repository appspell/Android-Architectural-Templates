package com.appspell.android.templates.mvi.simplelist.model

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers

@Deprecated("the newer implementation in MviListActivity and nested classes")
class SimpleListInteractorImpl : SimpleListInteractor {

    override fun requestList(): Observable<SimpleListViewState> {

        val simpleList = listOf("A", "B", "C", "D")

        return Observable
                .just(simpleList)
                .map { list: List<String> ->
                    SimpleListViewState(false, list)
                }
                .startWith(SimpleListViewState(true))
                .subscribeOn(Schedulers.io())
    }
}

