package com.appspell.android.templates.mvi.list.binder

import android.os.Bundle
import com.appspell.android.templates.mvi.list.model.ListInteractor
import com.appspell.android.templates.mvi.list.model.ListViewState
import com.appspell.android.templates.mvi.list.router.ListRouter
import com.appspell.android.templates.mvi.list.view.ListView
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

private const val TMP_USER_NAME = "appspell" //FIXME user name for testing

private const val SAVED_VIEW_STATE = "SAVED_VIEW_STATE"

interface ListBinder {
    fun create()
    fun bind()
    fun unBind()
    fun saveViewState(outState: Bundle)
    fun restoreViewState(savedInstanceState: Bundle?)
}

class ListBinderImpl(
        val view: ListView,
        val interactor: ListInteractor,
        val router: ListRouter
) : ListBinder {

    private val subscribers = CompositeDisposable()

    override fun create() {
        view.initViews()
    }

    override fun bind() {
        subscribers.add(interactor.requestList(TMP_USER_NAME)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI))

        subscribers.add(view.refreshIntent()
                .switchMap { interactor.refreshList() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::updateUI))

        subscribers.add(view.onItemClickIntent()
                .subscribe { router.openCatalogItem() })
    }

    override fun unBind() {
        subscribers.clear()
    }

    override fun saveViewState(outState: Bundle) {
        outState.putParcelable(SAVED_VIEW_STATE, interactor.getLastViewState())
    }

    override fun restoreViewState(savedInstanceState: Bundle?) {
        savedInstanceState?.let {
            interactor.setLastViewState(savedInstanceState.getParcelable(SAVED_VIEW_STATE))
        }
    }

    private fun updateUI(viewState: ListViewState) {
        interactor.setLastViewState(viewState)
        view.render(viewState)
    }
}

