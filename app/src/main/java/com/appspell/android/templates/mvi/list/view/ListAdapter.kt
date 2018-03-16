package com.appspell.android.templates.mvi.list.view

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import io.reactivex.subjects.PublishSubject

class ListAdapter : RecyclerView.Adapter<ListAdapter.ListViewHolder>() {

    var items = emptyList<DataEntity>()
        set(value) {
            field = value
            //@TODO use DiffUtil
            notifyDataSetChanged()
        }

    val onItemClick = PublishSubject.create<Any>()

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

        fun bind(item: DataEntity) {
            name.text = item.name
            description.text = item.description
            itemView.setOnClickListener { onItemClick.onNext(Any()) }
        }
    }
}