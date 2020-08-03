package com.appspell.android.templates.mvvm.list

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import javax.inject.Inject

abstract class MvvmListViewRepository {

    abstract fun fetchAsFlow(): Flow<State>
}

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {

    override fun fetchAsFlow(): Flow<State> =
        flow { emit(api.fetchList()) }
            .flowOn(Dispatchers.IO)
            .map { dto -> dto.convert() }
            .map { list -> State.Success(list) }
            .onStart<State> { emit(State.Loading) }
            .catch { ex -> emit(State.Error(ex.message.orEmpty())) }
            .flowOn(Dispatchers.Default)


    private fun List<ItemDTO>.convert() =
        map { item -> Item(title = item.name, description = item.description) }
}