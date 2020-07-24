package com.appspell.android.templates.mvvm.list

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

    override fun bind(containerView: View) {
        super.bind(containerView)
        with(containerView) {
            list.adapter = adapter
        }
    }

    override fun updateItems(items: List<Item>?) {
        adapter.submitList(items)
    }

    override fun showError(string: String?) {
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
        containerView?.apply {
            if (show == true) {
                progress.visible()
            } else {
                progress.gone()
            }
        }
    }
}