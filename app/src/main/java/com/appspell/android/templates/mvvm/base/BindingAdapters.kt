package com.appspell.android.templates.mvvm.base

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.RecyclerView

@BindingAdapter("recyclerAdapter")
fun bindRecyclerViewAdapter(view: RecyclerView, adapter: RecyclerView.Adapter<*>) {
    view.adapter = adapter
}

@BindingAdapter("isRefreshing", "onRefreshListener")
fun bindSwipeRefreshLayout(view: SwipeRefreshLayout, isRefreshing: Boolean, listener: SwipeRefreshLayout.OnRefreshListener?) {
    view.isRefreshing = isRefreshing
    view.setOnRefreshListener(listener)
}
