package com.appspell.android.templates.mvvm.list

import android.util.Log
import kotlinx.coroutines.flow.*
import javax.inject.Inject

abstract class MvvmListViewRepository {

    abstract fun fetch(): Flow<State>
}

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {

    override fun fetch(): Flow<State> =
        flow {
            Log.e("AAAA", "fetch - ${Thread.currentThread().name}")
            emit(api.fetchList())
        }
//            .flowOn(Dispatchers.IO)
            .map { dto ->
                Log.e("AAAA", "convert - ${Thread.currentThread().name}")
                dto.convert()
            }
            .map { list ->
                Log.e("AAAA", "success - ${Thread.currentThread().name}")
                State.Success(list)
            }
            .onStart {
                Log.e("AAAA", "start - ${Thread.currentThread().name}")
//                State.Loading
                State.Error("loading")
            }
            .catch { ex ->
                Log.e("AAAA", "catch - ${Thread.currentThread().name}", ex)
                State.Error(ex.message.orEmpty())
            }
//            .flowOn(Dispatchers.Default)


    private fun List<ItemDTO>.convert() =
        map { item -> Item(title = item.name, description = item.description) }
}