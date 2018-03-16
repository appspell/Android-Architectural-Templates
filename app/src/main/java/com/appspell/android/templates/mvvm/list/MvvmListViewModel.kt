package com.appspell.android.templates.mvvm.list

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import android.databinding.ObservableBoolean
import android.support.v4.widget.SwipeRefreshLayout
import com.appspell.android.templates.mvvm.base.map

private const val USER_FOR_TESTS = "appspell"

data class ListItem(val id: Long,
                    val name: String,
                    val description: String)

abstract class MvvmListViewModel : ViewModel() {
    val showProgress = ObservableBoolean(true)
    abstract val adapter: MvvmListAdapter
    abstract val onRefreshListener: SwipeRefreshLayout.OnRefreshListener
}

class MvvmListViewModelImpl : MvvmListViewModel() {

    private val repository: MvvmListViewRepository = MvvmListViewRepositoryImpl()

    override val adapter = MvvmListAdapter()

    override val onRefreshListener = SwipeRefreshLayout.OnRefreshListener(this::onRefresh)

    val itemMediator: LiveData<Unit> = repository.items.map(this::itemsChanged)

    init {
        repository.fetch(USER_FOR_TESTS)
    }

    private fun itemsChanged(list: List<DataEntity>) {
        if (list.isEmpty()) {
            showProgress.set(true)
        } else {
            showProgress.set(false)
            val items = list.map { ListItem(id = it.id, name = it.name, description = it.description) } //TODO should be in background
            adapter.items = items
        }
    }

    private fun onRefresh() {
        showProgress.set(true)
        repository.retry()
    }
}