package com.appspell.android.templates.mvvm.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.appspell.android.templates.mvvm.base.enqueueWithResult
import retrofit2.Call
import javax.inject.Inject

data class Result(
    val list: List<Item> = emptyList(),
    val error: Throwable? = null
)

abstract class MvvmListViewRepository {

    abstract val result: LiveData<Result>

    abstract fun fetch()

    abstract fun release()
}

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {

    override val result = MutableLiveData<Result>()

    private var call: Call<List<ItemDTO>>? = null

    override fun fetch() {
        call = api.fetchList().enqueueWithResult(
            success = { list ->
                result.value = Result(list = list?.map { it.toVO() } ?: emptyList())
            },
            error = {
                result.value = Result(error = it)
            }
        )
    }

    override fun release() {
        call?.cancel()
    }

    private fun ItemDTO.toVO() = Item(
        title = name,
        description = description
    )
}