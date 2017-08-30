package com.appspell.android.templates.mvi.list.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.SimpleListInteractorImpl
import com.appspell.android.templates.mvi.list.presenter.SimpleListPresenter
import com.appspell.android.templates.mvi.list.presenter.SimpleListPresenterImpl
import com.appspell.android.templates.mvi.list.router.SimpleListRouterImpl

class SimpleListActivity : AppCompatActivity() {

    lateinit var presenterSimple: SimpleListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvi_simple_list)

        val view = SimpleListViewImpl(findViewById(R.id.rootView))
        val interactor = SimpleListInteractorImpl()
        val router = SimpleListRouterImpl()
        presenterSimple = SimpleListPresenterImpl(view, interactor, router)
        presenterSimple.create()
    }

    override fun onResume() {
        super.onResume()
        presenterSimple.bind()
    }

    override fun onPause() {
        presenterSimple.unBind()
        super.onPause()
    }
}
