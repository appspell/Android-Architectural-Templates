package com.appspell.android.templates.mvi.list.view

interface SimpleListView {
    fun initViews(listener: OnSimpleListItemClick)
    fun updateList(list: List<String>)
    fun showProgress()
    fun hideProgress()
}