package com.appspell.android.templates.mvi.list.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.list.binder.ListBinder
import com.appspell.android.templates.mvi.list.binder.ListBinderImpl
import com.appspell.android.templates.mvi.list.model.ListInteractorImpl
import com.appspell.android.templates.mvi.list.router.ListRouterImpl

class MviListActivity : AppCompatActivity() {

    private lateinit var binder: ListBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvi_list)

        val view = ListViewImpl(findViewById(R.id.rootView))
        val interactor = ListInteractorImpl()
        val router = ListRouterImpl(this)
        binder = ListBinderImpl(view, interactor, router)
        binder.create()
        binder.restoreViewState(savedInstanceState)
    }

    override fun onResume() {
        super.onResume()
        binder.bind()
    }

    override fun onPause() {
        super.onPause()
        binder.unBind()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        binder.saveViewState(outState)
    }
}
