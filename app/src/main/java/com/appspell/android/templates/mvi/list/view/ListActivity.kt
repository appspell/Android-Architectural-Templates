package com.appspell.android.templates.mvi.list.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.model.ListInteractorImpl
import com.appspell.android.templates.mvi.list.presenter.ListPresenter
import com.appspell.android.templates.mvi.list.presenter.ListPresenterImpl
import com.appspell.android.templates.mvi.list.router.ListRouterImpl

class ListActivity : AppCompatActivity() {

    lateinit var presenter: ListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvi_list)

        val view = ListViewImpl(findViewById(R.id.rootView))
        val interactor = ListInteractorImpl()
        val router = ListRouterImpl()
        presenter = ListPresenterImpl(view, interactor, router)
        presenter.create()
        presenter.restoreViewState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        presenter.bind()
    }

    override fun onPause() {
        presenter.unBind()
        super.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        presenter.saveViewState(outState)
    }

}
