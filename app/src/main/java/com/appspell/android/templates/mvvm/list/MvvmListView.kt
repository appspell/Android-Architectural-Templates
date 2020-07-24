package com.appspell.android.templates.mvvm.list

import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import com.appspell.android.templates.mvvm.base.gone
import com.appspell.android.templates.mvvm.base.visible
import kotlinx.android.synthetic.main.fragment_mvvm_list.view.*
import javax.inject.Inject

abstract class MvvmListView {

    protected var containerView: View? = null

    @CallSuper
    open fun bind(containerView: View) {
        this.containerView = containerView
    }

    abstract fun updateItems(items: List<Item>?)

    abstract fun showError(string: String?)

    abstract fun showLoader(show: Boolean?)
}

class MvvmListViewImpl @Inject constructor() : MvvmListView() {

    private val adapter = MvvmListAdapter()

    init {
        Log.i(DEBUG_LOG_TAG, "View.init")
    }

    override fun bind(containerView: View) {
        Log.i(DEBUG_LOG_TAG, "View.bind")
        super.bind(containerView)
        with(containerView) {
            list.adapter = adapter
        }
    }

    override fun updateItems(items: List<Item>?) {
        Log.i(DEBUG_LOG_TAG, "View.updateItems")
        adapter.submitList(items)
    }

    override fun showError(string: String?) {
        Log.i(DEBUG_LOG_TAG, "View.showError")
        containerView?.apply {
            if (string?.isNotEmpty() == true) {
                error.text = string
                error.visible()
            } else {
                error.gone()
            }
        }
    }

    override fun showLoader(show: Boolean?) {
        Log.i(DEBUG_LOG_TAG, "View.showLoader")
        containerView?.apply {
            if (show == true) {
                progress.visible()
            } else {
                progress.gone()
            }
        }
    }
}