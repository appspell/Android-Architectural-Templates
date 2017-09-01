package com.appspell.android.templates.mvi.simplelist.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appspell.android.templates.R

class SimpleListAdapter(val listener: OnSimpleListItemClick) : RecyclerView.Adapter<SimpleListAdapter.ListViewHolder>() {

    private var items = emptyList<String>()

    fun updateList(newList: List<String>) {
        items = newList
        //@TODO use DiffUtil
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder? {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_simple_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name) as TextView

        fun bind(item: String, listener: OnSimpleListItemClick) {
            name.text = item
            itemView.setOnClickListener { listener.onItemClicked() }
        }
    }

}