package com.appspell.android.templates.mvvm.list

sealed class State {
    object Loading : State()
    data class Error(val error: String) : State()
    data class Success(val list: List<Item>) : State()
}

data class Item(
    val title: String,
    val description: String
)