package com.appspell.android.templates.mvi.list.view

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.ListViewState
import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import io.reactivex.subjects.PublishSubject

interface ListView {
    fun initViews()
    fun refreshIntent(): PublishSubject<Any>
    fun onItemClickIntent(): PublishSubject<Any>
    fun render(viewState: ListViewState)
}

class ListViewImpl(rootView: View) : ListView {
    private val list: RecyclerView = rootView.findViewById(R.id.list)
    private var refresh: SwipeRefreshLayout = rootView.findViewById(R.id.refresh)
    private val onRefreshSubject = PublishSubject.create<Any>()

    override fun initViews() {
        list.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            adapter = ListAdapter()
        }

        refresh.setOnRefreshListener { onRefreshSubject.onNext(true) }
    }

    override fun refreshIntent(): PublishSubject<Any> = onRefreshSubject

    override fun onItemClickIntent(): PublishSubject<Any> = (list.adapter as ListAdapter).onItemClick

    override fun render(viewState: ListViewState) {
        changeLoadingState(viewState)
        updateList(viewState.list)
    }

    private fun changeLoadingState(viewState: ListViewState) {
        if (viewState.loading) {
            showProgress()
        } else {
            hideProgress()
        }
    }

    private fun showProgress() {
        refresh.isRefreshing = true
        list.visibility = View.GONE
    }

    private fun hideProgress() {
        refresh.isRefreshing = false
        list.visibility = View.VISIBLE
    }

    private fun updateList(entiies: List<DataEntity>) {
        if (entiies.isEmpty()) {
            return
        }
        Log.d("TAG", entiies.toString())
        (list.adapter as ListAdapter).items = entiies
    }
}