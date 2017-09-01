package com.appspell.android.templates.mvi.simplelist.view

interface SimpleListView {
    fun initViews(listener: OnSimpleListItemClick)
    fun updateList(list: List<String>)
    fun showProgress()
    fun hideProgress()
}