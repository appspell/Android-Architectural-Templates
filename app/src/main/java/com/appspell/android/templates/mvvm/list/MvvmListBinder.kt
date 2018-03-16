package com.appspell.android.templates.mvvm.list

import android.arch.lifecycle.LifecycleOwner
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import com.appspell.android.templates.databinding.ActivityMvvmListBinding

class MvvmListBinder(activity: AppCompatActivity,
                     binding: ActivityMvvmListBinding) {

    private val viewModel = ViewModelProviders.of(activity).get(MvvmListViewModelImpl::class.java)

    init {
        binding.viewModel = viewModel
    }

    fun bind(owner: LifecycleOwner) {
        viewModel.itemMediator.observe(owner, Observer { doNothing() })
    }

    private fun doNothing() {} //TODO it's a workaround against optimizations in compilator
}