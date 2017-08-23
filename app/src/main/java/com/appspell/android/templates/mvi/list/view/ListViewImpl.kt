package com.appspell.android.templates.mvi.list.view

import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.entity.DataEntity

class ListViewImpl(rootView: View) : ListView {

    private val list: RecyclerView

    private lateinit var adapter: ListAdapter

    init {
        list = rootView.findViewById(R.id.list) as RecyclerView
    }

    override fun initViews(listener: OnListItemClick) {
        list.layoutManager = LinearLayoutManager(list.context, LinearLayoutManager.VERTICAL, false)
        adapter = ListAdapter(listener)
        list.adapter = adapter
    }

    override fun updateList(list: List<DataEntity>) {
        Log.d("TAG", list.toString())
        adapter.updateList(list)
    }
}