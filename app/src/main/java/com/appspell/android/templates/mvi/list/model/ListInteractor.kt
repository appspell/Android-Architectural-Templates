package com.appspell.android.templates.mvi.list.model

import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import io.reactivex.Observable

interface ListInteractor {

    fun requestList(): Observable<List<DataEntity>>?
}