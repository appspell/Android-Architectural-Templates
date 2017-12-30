package com.appspell.android.templates.mvi.simplelist.view

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ProgressBar
import com.appspell.android.templates.R

class SimpleListViewImpl(activity: Activity) : SimpleListView {
    private var progress: ProgressBar = activity.findViewById(R.id.progress)
    private val list: RecyclerView = activity.findViewById(R.id.list)

    private lateinit var adapterSimple: SimpleListAdapter

    override fun initViews(listener: OnSimpleListItemClick) {
        list.layoutManager = LinearLayoutManager(list.context, LinearLayoutManager.VERTICAL, false)
        adapterSimple = SimpleListAdapter(listener)
        list.adapter = adapterSimple
    }

    override fun updateList(list: List<String>) {
        if (list.isEmpty()) {
            return
        }
        Log.d("TAG", list.toString())
        adapterSimple.updateList(list)
    }

    override fun showProgress() {
        progress.visibility = View.VISIBLE
        list.visibility = View.GONE
    }

    override fun hideProgress() {
        progress.visibility = View.GONE
        list.visibility = View.VISIBLE
    }
}