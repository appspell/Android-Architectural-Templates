package com.appspell.android.templates.mvvm.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import java.io.IOException
import javax.inject.Inject

data class Result(val list: List<Item> = emptyList(), val error: Throwable? = null)

abstract class MvvmListViewRepository {

    abstract fun fetch(coroutineScope: CoroutineScope): LiveData<Result>
}

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {

    override fun fetch(coroutineScope: CoroutineScope): LiveData<Result> =
        liveData(context = coroutineScope.coroutineContext + Dispatchers.IO) {
            try {
                val list = api.fetchList().map { dto -> dto.convert() }
                emit(Result(list = list))
            } catch (ex: IOException) {
                emit(Result(error = ex))
            }
        }

    private fun ItemDTO.convert() = Item(title = name, description = description)
}