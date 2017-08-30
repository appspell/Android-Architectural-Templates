package com.appspell.android.templates.mvi.list.presenter

import android.os.Bundle

interface ListPresenter {
    fun create()
    fun bind()
    fun unBind()
    fun saveViewState(outState: Bundle)
    fun restoreViewState(savedInstanceState: Bundle?)
}