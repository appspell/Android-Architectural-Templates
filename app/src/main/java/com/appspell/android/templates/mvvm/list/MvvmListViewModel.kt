package com.appspell.android.templates.mvvm.list

import androidx.lifecycle.*
import com.appspell.android.templates.mvvm.base.doOnNext
import kotlinx.coroutines.Dispatchers

abstract class MvvmListViewModel : ViewModel() {
    abstract val items: LiveData<List<Item>?>
    abstract val error: LiveData<String?>
    abstract val showLoader: LiveData<Boolean?>

    abstract val openScreenEvent: LiveData<Int?>

    abstract val result: LiveData<Result>
}

class MvvmListViewModelImpl(repository: MvvmListViewRepository) : MvvmListViewModel() {

    override val items = MutableLiveData<List<Item>?>()
    override val error = MutableLiveData<String?>()
    override val showLoader = MutableLiveData<Boolean?>()

    override val openScreenEvent = MutableLiveData<Int?>()

    override val result =
        liveData(context = viewModelScope.coroutineContext + Dispatchers.IO) {
            emit(repository.fetch()) // background thread
        }.doOnNext { result ->
            handleResult(result) // main thread
        }

    init {
        showLoader.value = true
    }

    private fun handleResult(result: Result) {
        items.value = result.list
        error.value = result.error?.message
        showLoader.value = false
    }
}