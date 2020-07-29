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

    abstract fun render(state: State)
}

class MvvmListViewImpl @Inject constructor() : MvvmListView() {

    private val adapter = MvvmListAdapter()

    override fun bind(containerView: View) {
        super.bind(containerView)
        with(containerView) {
            list.adapter = adapter
        }
    }

    override fun render(state: State) {
        containerView?.apply {
            emptyState()

            when (state) {
                State.Loading -> progress.visible()
                is State.Error -> {
                    error.text = state.error
                    error.visible()
                }
                is State.Success -> {
                    adapter.submitList(state.list)
                    list.visible()
                }
            }
        }
    }

    private fun View.emptyState() {
        progress.gone()
        error.gone()
        list.gone()
    }
}