package com.appspell.android.templates.mvi.simplelist.di

import android.app.Activity

import com.appspell.android.templates.mvi.simplelist.model.SimpleListInteractor
import com.appspell.android.templates.mvi.simplelist.model.SimpleListInteractorImpl
import com.appspell.android.templates.mvi.simplelist.presenter.SimpleListPresenter
import com.appspell.android.templates.mvi.simplelist.presenter.SimpleListPresenterImpl
import com.appspell.android.templates.mvi.simplelist.router.SimpleListRouter
import com.appspell.android.templates.mvi.simplelist.router.SimpleListRouterImpl
import com.appspell.android.templates.mvi.simplelist.view.SimpleListView
import com.appspell.android.templates.mvi.simplelist.view.SimpleListViewImpl

import dagger.Module
import dagger.Provides

@Module
class SimpleListModule(private val activity: Activity) {

    @Provides
    fun provideSimpleListView(): SimpleListView {
        return SimpleListViewImpl(activity)
    }

    @Provides
    fun provideSimpleListInteractor(): SimpleListInteractor {
        return SimpleListInteractorImpl()
    }

    @Provides
    fun provideSimpleListRouter(): SimpleListRouter {
        return SimpleListRouterImpl()
    }

    @Provides
    fun provideSimpleListPresenter(view: SimpleListView,
                                   interactor: SimpleListInteractor,
                                   router: SimpleListRouter): SimpleListPresenter {
        return SimpleListPresenterImpl(view, interactor, router)
    }
}
