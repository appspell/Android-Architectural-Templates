package com.appspell.android.templates.mvvm.list

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appspell.android.templates.R

class MvvmListAdapter : RecyclerView.Adapter<MvvmListAdapter.ListViewHolder>() {

    var items = emptyList<ListItem>()
        set(value) {
            field = value
            //@TODO use DiffUtil
            notifyDataSetChanged()
        }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position])
    }

    inner class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val name: TextView = itemView.findViewById(R.id.name)
        private val description: TextView = itemView.findViewById(R.id.description)

        fun bind(item: ListItem) {
            name.text = item.name
            description.text = item.description
        }
    }
}