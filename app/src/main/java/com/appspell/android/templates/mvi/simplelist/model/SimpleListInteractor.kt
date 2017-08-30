package com.appspell.android.templates.mvi.list.model

import io.reactivex.Observable

interface SimpleListInteractor {
    fun requestList(): Observable<SimpleListViewState>
}