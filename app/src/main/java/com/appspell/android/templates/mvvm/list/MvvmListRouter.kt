package com.appspell.android.templates.mvvm.list

import javax.inject.Inject

interface MvvmListRouter {
    fun openDetails(id: Int?)
}

class MvvmListRouterImpl @Inject constructor() : MvvmListRouter {
    override fun openDetails(id: Int?) {
        // TODO do navigation here
    }
}