package com.appspell.android.templates.mvi.simplelist.di

import com.appspell.android.templates.mvi.simplelist.view.SimpleListActivity

import dagger.Component

@Component(modules = arrayOf(SimpleListModule::class))
interface SimpleListComponent {
    fun inject(simpleListActivity: SimpleListActivity)
}
