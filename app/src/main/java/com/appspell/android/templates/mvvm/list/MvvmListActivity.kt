package com.appspell.android.templates.mvvm.list

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.mvvm.list.di.DaggerMvvmListComponent
import kotlinx.android.synthetic.main.activity_mvvm_list.*
import javax.inject.Inject

const val DEBUG_LOG_TAG = "LiveDataInvestigation"

class MvvmListActivity : AppCompatActivity() {

    init {
        Log.i(DEBUG_LOG_TAG, "Activity.init")
    }

    @Inject
    lateinit var binder: MvvmListBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.i(DEBUG_LOG_TAG, "Activity.onCreate")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_list)

        DaggerMvvmListComponent.builder()
            .activity(this)
            .build()
            .inject(this)

        binder.bindView(root)
        binder.bindLifecycle(this)
    }

    override fun onResume() {
        super.onResume()
        Log.i(DEBUG_LOG_TAG, "Activity.onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.i(DEBUG_LOG_TAG, "Activity.onPause")
    }
}
