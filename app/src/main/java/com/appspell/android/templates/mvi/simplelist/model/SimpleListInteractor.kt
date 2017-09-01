package com.appspell.android.templates.mvi.simplelist.model

import io.reactivex.Observable

interface SimpleListInteractor {
    fun requestList(): Observable<SimpleListViewState>
}