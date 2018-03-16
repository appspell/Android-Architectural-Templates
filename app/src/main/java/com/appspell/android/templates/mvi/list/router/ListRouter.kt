package com.appspell.android.templates.mvi.list.router

import android.content.Context
import android.widget.Toast
import com.appspell.android.templates.R

interface ListRouter {
    fun openCatalogItem()
}

class ListRouterImpl(private val context: Context) : ListRouter {
    override fun openCatalogItem() {
        Toast.makeText(context, R.string.stub_on_item_click, Toast.LENGTH_SHORT).show()
    }
}
