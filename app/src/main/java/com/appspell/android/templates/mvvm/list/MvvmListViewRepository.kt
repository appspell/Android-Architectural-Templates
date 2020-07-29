package com.appspell.android.templates.mvvm.list

import java.io.IOException
import javax.inject.Inject

data class Result(val list: List<Item> = emptyList(), val error: Throwable? = null)

abstract class MvvmListViewRepository {

    abstract suspend fun fetch(): Result
}

class MvvmListViewRepositoryImpl @Inject constructor(
    private val api: ApiService
) : MvvmListViewRepository() {

    override suspend fun fetch(): Result =
        try {
            val list = api.fetchList().map { dto -> dto.convert() }
            Result(list = list)
        } catch (ex: IOException) {
            Result(error = ex)
        }

    private fun ItemDTO.convert() = Item(title = name, description = description)
}