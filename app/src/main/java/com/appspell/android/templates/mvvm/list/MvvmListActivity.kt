package com.appspell.android.templates.mvvm.list

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.mvvm.list.di.DaggerMvvmListComponent
import kotlinx.android.synthetic.main.activity_mvvm_list.*
import javax.inject.Inject

class MvvmListActivity : AppCompatActivity() {

    @Inject
    lateinit var binder: MvvmListBinder

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_list)

        DaggerMvvmListComponent.builder()
            .activity(this)
            .build()
            .inject(this)

        binder.bindView(root)
        binder.bindLifecycle(this)
    }
}
