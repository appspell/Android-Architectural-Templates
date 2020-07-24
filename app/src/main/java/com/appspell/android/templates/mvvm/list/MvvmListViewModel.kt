package com.appspell.android.templates.mvvm.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.appspell.android.templates.mvvm.base.doOnNext

abstract class MvvmListViewModel : ViewModel() {
    abstract val items: LiveData<List<Item>?>
    abstract val error: LiveData<String?>
    abstract val showLoader: LiveData<Boolean?>

    abstract val result: LiveData<Result>
}

class MvvmListViewModelImpl(repository: MvvmListViewRepository) : MvvmListViewModel() {

    override val items = MutableLiveData<List<Item>?>()
    override val error = MutableLiveData<String?>()
    override val showLoader = MutableLiveData<Boolean?>()

    override val result = repository.result.doOnNext { result -> handleResult(result) }

    init {
        Log.i(DEBUG_LOG_TAG, "ViewModel.init")

        repository.fetch()

        showLoader.value = true
    }

    private fun handleResult(result: Result) {
        Log.i(DEBUG_LOG_TAG, "ViewModel.handleResult")

        items.value = result.list
        error.value = result.error?.message

        showLoader.value = false
    }

    override fun onCleared() {
        super.onCleared()
        Log.i(DEBUG_LOG_TAG, "ViewModel.onCleared")
    }
}