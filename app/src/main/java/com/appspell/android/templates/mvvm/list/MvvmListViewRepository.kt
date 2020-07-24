package com.appspell.android.templates.mvvm.list

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appspell.android.templates.mvvm.base.enqueueWithResult
import javax.inject.Inject

data class Result(
    val list: List<Item> = emptyList(),
    val error: Throwable? = null
)

abstract class MvvmListViewRepository {

    abstract val result: LiveData<Result>

    abstract fun fetch()
}

class MvvmListViewRepositoryImpl @Inject constructor(private val api: ApiService) :
    MvvmListViewRepository() {

    override val result = MutableLiveData<Result>()

    init {
        Log.i(DEBUG_LOG_TAG, "Repository.init")
    }

    override fun fetch() {
        Log.i(DEBUG_LOG_TAG, "Repository.fetch")

        api.fetchList().enqueueWithResult(
            success = { list ->
                result.value = Result(list = list?.map { it.toVO() } ?: emptyList())
            },
            error = {
                result.value = Result(error = it)
            }
        )
    }

    private fun ItemDTO.toVO() = Item(
        title = name,
        description = description
    )
}