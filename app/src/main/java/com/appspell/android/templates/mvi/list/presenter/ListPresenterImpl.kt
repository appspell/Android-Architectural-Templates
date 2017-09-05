package com.appspell.android.templates.mvi.list.presenter

import android.os.Bundle
import com.appspell.android.templates.mvi.list.model.ListInteractor
import com.appspell.android.templates.mvi.list.model.ListViewState
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

    val SAVED_VIEW_TATE = "SAVED_VIEW_STATE"

    lateinit var listSubscriber: Disposable
    lateinit var refreshSubscriber: Disposable

    override fun create() {
        view.initViews(this)
    }

    override fun bind() {
        listSubscriber = interactor.requestList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState: ListViewState -> updateUI(viewState) }

        refreshSubscriber = view.refreshIntent()
                .switchMap { interactor.requestList() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { viewState: ListViewState -> updateUI(viewState) }
    }

    override fun unBind() {
        if (!listSubscriber.isDisposed) {
            listSubscriber.dispose()
        }
        if (!refreshSubscriber.isDisposed) {
            refreshSubscriber.dispose()
        }
    }

    override fun saveViewState(outState: Bundle) {
        outState.putParcelable(SAVED_VIEW_TATE, interactor.getLastViewState())
    }

    override fun restoreViewState(savedInstanceState: Bundle?) {
        if (savedInstanceState == null) {
            return
        }
        interactor.setLastViewState(savedInstanceState.getParcelable(SAVED_VIEW_TATE))
    }

    override fun onItemClicked() {
        router.openCatalogItem()
    }

    private fun updateUI(viewState: ListViewState) {
        interactor.setLastViewState(viewState);
        if (viewState.loading) {
            view.showProgress()
        } else {
            view.hideProgress()
        }
        view.updateList(viewState.list)
    }
}

