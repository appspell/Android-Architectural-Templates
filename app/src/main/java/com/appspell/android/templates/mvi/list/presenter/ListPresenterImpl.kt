package com.appspell.android.templates.mvi.list.presenter

import com.appspell.android.templates.mvi.list.model.ListInteractor
import com.appspell.android.templates.mvi.list.model.entity.DataEntity
import com.appspell.android.templates.mvi.list.router.ListRouter
import com.appspell.android.templates.mvi.list.view.ListView
import com.appspell.android.templates.mvi.list.view.OnListItemClick
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable

class ListPresenterImpl(
        val view: ListView,
        val interactor: ListInteractor,
        val router: ListRouter
) : ListPresenter, OnListItemClick {

    lateinit var disposable: Disposable

    override fun init() {
        view.initViews(this)
    }

    override fun bind() {
        disposable = interactor.requestList()!!
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { list: List<DataEntity> -> view.updateList(list) }
    }

    override fun unBind() {
        if (!disposable.isDisposed) {
            disposable.dispose()
        }
    }

    override fun onItemClicked() {
        router.openCatalogItem()
    }
}