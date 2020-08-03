package com.appspell.android.templates.mvvm.list

import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

interface MvvmListBinder {
    fun bindView(containerView: View)
    fun bindLifecycle(owner: LifecycleOwner)
}

class MvvmListBinderImpl @Inject constructor(
    private val fragment: Fragment,
    private val view: MvvmListView,
    private val router: MvvmListRouter,
    private val viewModel: MvvmListViewModel
) : MvvmListBinder {

    override fun bindView(containerView: View) {
        view.bind(containerView)
    }

    override fun bindLifecycle(owner: LifecycleOwner) {
        fragment.lifecycleScope.launchWhenResumed {
            viewModel.state
                .onEach { state ->
                    Log.e("AAAA", "binder-onEach - ${Thread.currentThread().name}")
                    view.render(state)
                }
                .catch { ex ->
                    Log.e("AAAA", "binder-catch - ${Thread.currentThread().name}", ex)
                }
                .collect()
        }
    }
}