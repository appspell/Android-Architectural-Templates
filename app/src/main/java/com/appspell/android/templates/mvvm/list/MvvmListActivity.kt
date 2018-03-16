package com.appspell.android.templates.mvvm.list

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.appspell.android.templates.R
import com.appspell.android.templates.databinding.ActivityMvvmListBinding

class MvvmListActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mvvm_list)
        val binding: ActivityMvvmListBinding = DataBindingUtil.setContentView(this, R.layout.activity_mvvm_list)
        val binder = MvvmListBinder(this, binding)
        binder.bind(this)
    }
}
