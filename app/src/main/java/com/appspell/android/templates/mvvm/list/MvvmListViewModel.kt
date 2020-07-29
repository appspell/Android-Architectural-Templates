package com.appspell.android.templates.mvvm.list

import androidx.lifecycle.*

abstract class MvvmListViewModel : ViewModel() {
    abstract val openScreenEvent: LiveData<Int?>

    abstract val state: LiveData<State>
}

class MvvmListViewModelImpl(repository: MvvmListViewRepository) : MvvmListViewModel() {

    override val openScreenEvent = MutableLiveData<Int?>()

    override val state =
        liveData {
            emitSource(repository.fetch(viewModelScope)) // background thread
        }
}