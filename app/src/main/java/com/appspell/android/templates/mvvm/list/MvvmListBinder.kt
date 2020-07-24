package com.appspell.android.templates.mvvm.list

import android.util.Log
import android.view.View
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import javax.inject.Inject

interface MvvmListBinder {
    fun bindView(containerView: View)
    fun bindLifecycle(owner: LifecycleOwner)
}

class MvvmListBinderImpl @Inject constructor(
    private val view: MvvmListView,
    private val router: MvvmListRouter,
    private val viewModel: MvvmListViewModel
) : MvvmListBinder {

    init {
        Log.i(DEBUG_LOG_TAG, "Binder.init")
    }

    override fun bindView(containerView: View) {
        Log.i(DEBUG_LOG_TAG, "Binder.bindView")
        view.bind(containerView)
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        Log.i(DEBUG_LOG_TAG, "Binder.bindLifecycle")
        viewModel.items.observe(owner, Observer(view::updateItems))
        viewModel.error.observe(owner, Observer(view::showError))
        viewModel.showLoader.observe(owner, Observer(view::showLoader))

        viewModel.openScreenEvent.observe(owner, Observer(router::openDetails))

        viewModel.result.observe(owner, Observer {})
    }
}