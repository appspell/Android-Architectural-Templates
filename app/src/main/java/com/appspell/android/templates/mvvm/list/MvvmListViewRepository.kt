package com.appspell.android.templates.mvvm.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject

abstract class MvvmListViewRepository {

    abstract fun fetch(coroutineScope: CoroutineScope): LiveData<State>
}

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {

    override fun fetch(coroutineScope: CoroutineScope): LiveData<State> =
        liveData(context = coroutineScope.coroutineContext + Dispatchers.IO) {
            try {
                emit(State.Loading)
                val list = api.fetchList().map { dto -> dto.convert() }
                emit(State.Success(list = list))
            } catch (ex: IOException) {
                emit(State.Error(ex.message.orEmpty()))
            }
        }

    private fun ItemDTO.convert() = Item(title = name, description = description)
}