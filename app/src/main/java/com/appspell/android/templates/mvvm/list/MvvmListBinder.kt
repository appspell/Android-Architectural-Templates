package com.appspell.android.templates.mvvm.list

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

    override fun bindView(containerView: View) {
        view.bind(containerView)
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        viewModel.items.observe(owner, Observer(view::updateItems))
        viewModel.error.observe(owner, Observer(view::showError))
        viewModel.showLoader.observe(owner, Observer(view::showLoader))

        viewModel.result.observe(owner, Observer {})
    }
}