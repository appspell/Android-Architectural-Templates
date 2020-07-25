package com.appspell.android.templates.mvvm.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appspell.android.templates.mvvm.base.enqueueInBackground
import kotlinx.coroutines.CoroutineScope
import javax.inject.Inject

data class Result(val list: List<Item> = emptyList(), val error: Throwable? = null)

abstract class MvvmListViewRepository {

    abstract val result: LiveData<Result>

    abstract fun fetch(coroutineScope: CoroutineScope)
}

//data class Result(val list: List<Item>, val error: Throwable)

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {
    override val result = MutableLiveData<Result>()

    override fun fetch(coroutineScope: CoroutineScope) {
        api.fetchList().enqueueInBackground(
            background = { dto ->
                Log.i("COR", "`background` in thread ${Thread.currentThread().name}")
                dto?.map { it.convert() }
            },
            success = { list ->
                Log.i("COR", "`success` in thread ${Thread.currentThread().name}")
                result.value = Result(list = list ?: emptyList())
            },
            error = {
                result.value = Result(error = it)
            },
            coroutineScope = coroutineScope
        )
    }

    private fun ItemDTO.convert() = Item(title = name, description = description)
}