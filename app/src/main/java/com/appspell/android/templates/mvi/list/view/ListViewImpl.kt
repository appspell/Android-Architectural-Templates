package com.appspell.android.templates.mvi.list.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.entity.DataEntity

class ListViewImpl(rootView: View) : ListView {
    private var progress: ProgressBar = rootView.findViewById(R.id.progress) as ProgressBar
    private val list: RecyclerView = rootView.findViewById(R.id.list) as RecyclerView

    private lateinit var adapter: ListAdapter

    override fun initViews(listener: OnListItemClick) {
        list.layoutManager = LinearLayoutManager(list.context, LinearLayoutManager.VERTICAL, false)
        adapter = ListAdapter(listener)
        list.adapter = adapter
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
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