package com.appspell.android.templates.mvi.list.model

import io.reactivex.Observable

interface ListInteractor {

    fun requestList(user: String): Observable<ListViewState>
    fun refreshList(): Observable<ListViewState>
    fun setLastViewState(viewState: ListViewState)
    fun getLastViewState(): ListViewState?
}