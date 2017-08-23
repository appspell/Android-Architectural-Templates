package com.appspell.android.templates.mvi.list.presenter

import com.appspell.android.templates.mvi.list.model.ListInteractor
import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import com.appspell.android.templates.mvi.list.router.ListRouter
import com.appspell.android.templates.mvi.list.view.ListView
import com.appspell.android.templates.mvi.list.view.OnListItemClick
import io.reactivex.android.schedulers.AndroidSchedulers

class ListPresenterImpl(
        val view: ListView,
        val interactor: ListInteractor,
        val router: ListRouter
) : ListPresenter, OnListItemClick {

    override fun init() {
        view.initViews(this)
    }

    override fun bind() {
        interactor.requestList()!!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list: List<DataEntity> -> view.updateList(list) }
    }

    override fun unBind() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemClicked() {
        router.openCatalogItem()
    }
}