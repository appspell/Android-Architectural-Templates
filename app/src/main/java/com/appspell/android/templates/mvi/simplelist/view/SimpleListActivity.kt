package com.appspell.android.templates.mvi.simplelist.view

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.mvi.simplelist.di.DaggerSimpleListComponent
import com.appspell.android.templates.mvi.simplelist.di.SimpleListModule
import com.appspell.android.templates.mvi.simplelist.presenter.SimpleListPresenter
import javax.inject.Inject

@Deprecated("the newer implementation in MviListActivity and nested classes")
class SimpleListActivity : AppCompatActivity() {

    @Inject
    lateinit var presenterSimple: SimpleListPresenter

    override fun onCreate(savedInstanceState
                          : Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvi_simple_list)

        DaggerSimpleListComponent
                .builder()
                .simpleListModule(SimpleListModule(this))
                .build()
                .inject(this)

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
