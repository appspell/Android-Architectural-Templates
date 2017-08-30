package com.appspell.android.templates.mvi.list.view

import com.appspell.android.templates.mvi.list.model.entity.DataEntity

interface ListView {
    fun initViews(listener: OnListItemClick)
    fun showProgress()
    fun hideProgress()
    fun updateList(list: List<DataEntity>)
}