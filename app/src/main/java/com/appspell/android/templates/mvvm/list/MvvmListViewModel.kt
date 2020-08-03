package com.appspell.android.templates.mvvm.list

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@ExperimentalCoroutinesApi
abstract class MvvmListViewModel : ViewModel() {

    abstract val state: StateFlow<State>
}

@ExperimentalCoroutinesApi
class MvvmListViewModelImpl(repository: MvvmListViewRepository) : MvvmListViewModel() {

    override val state = MutableStateFlow<State>(State.Loading)

    init {
        repository.fetchAsFlow()
            .onEach { newState ->
                state.value = newState
            }
            .launchIn(viewModelScope)
    }
}