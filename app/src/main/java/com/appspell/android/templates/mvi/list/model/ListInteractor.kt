package com.appspell.android.templates.mvi.list.model

import io.reactivex.Observable

interface ListInteractor {

    fun requestList(): Observable<ListViewState>
    fun setLastViewState(viewState: ListViewState)
    fun getLastViewState(): ListViewState?
}