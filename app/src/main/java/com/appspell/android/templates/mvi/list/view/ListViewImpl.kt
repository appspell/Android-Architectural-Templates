package com.appspell.android.templates.mvi.list.view

import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import io.reactivex.subjects.PublishSubject

class ListViewImpl(rootView: View) : ListView {
    private val list: RecyclerView = rootView.findViewById(R.id.list)
    private var refresh: SwipeRefreshLayout = rootView.findViewById(R.id.refresh)

    private lateinit var adapter: ListAdapter
    private val onRefreshSubject = PublishSubject.create<Boolean>()

    override fun initViews(listener: OnListItemClick) {
        list.layoutManager = LinearLayoutManager(list.context, LinearLayoutManager.VERTICAL, false)
        adapter = ListAdapter(listener)
        list.adapter = adapter

        refresh.setOnRefreshListener { onRefreshSubject.onNext(true) }
    }

    override fun refreshIntent(): PublishSubject<Boolean> = onRefreshSubject

    override fun showProgress() {
        refresh.isRefreshing = true
        list.visibility = View.GONE
    }

    override fun hideProgress() {
        refresh.isRefreshing = false
        list.visibility = View.VISIBLE
    }

    override fun updateList(list: List<DataEntity>) {
        if (list.isEmpty()) {
            return
        }
        Log.d("TAG", list.toString())
        adapter.updateList(list)
    }
}