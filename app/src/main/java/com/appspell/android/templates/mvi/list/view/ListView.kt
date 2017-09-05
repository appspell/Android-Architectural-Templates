package com.appspell.android.templates.mvi.list.view

import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import io.reactivex.subjects.PublishSubject

interface ListView {
    fun initViews(listener: OnListItemClick)
    fun showProgress()
    fun hideProgress()
    fun updateList(list: List<DataEntity>)
    fun refreshIntent(): PublishSubject<Boolean>
}