package com.appspell.android.templates.mvi.list.view

import com.appspell.android.templates.mvi.list.model.entity.DataEntity

interface ListView {
    fun updateList(list: List<DataEntity>)
    fun initViews(listener: OnListItemClick)
}