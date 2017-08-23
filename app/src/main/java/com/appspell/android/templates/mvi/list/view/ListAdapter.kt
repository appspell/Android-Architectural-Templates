package com.appspell.android.templates.mvi.list.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.entity.DataEntity

class ListAdapter(val listener: OnListItemClick) : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    private var items = emptyList<DataEntity>()

    fun updateList(newList: List<DataEntity>) {
        items = newList
        //@TODO use DiffUtil
        notifyDataSetChanged()
    }

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder? {
        return ListViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_list, parent, false))
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        holder.bind(items[position], listener)
    }

    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val name: TextView = itemView.findViewById(R.id.name) as TextView
        val description: TextView = itemView.findViewById(R.id.description) as TextView

        fun bind(item: DataEntity, listener: OnListItemClick) {
            name.text = item.name
            description.text = item.description
            itemView.setOnClickListener { listener.onItemClicked() }
        }
    }


}