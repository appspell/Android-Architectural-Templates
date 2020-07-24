package com.appspell.android.templates.mvvm.base

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.lifecycle.ViewModelStoreOwner

inline fun <reified T : ViewModel> FragmentActivity.viewModel(): T =
    ViewModelProviders.of(this).get(T::class.java)

inline fun <reified T : ViewModel> Fragment.viewModel(): T =
    ViewModelProviders.of(this).get(T::class.java)

inline fun <reified T : ViewModel> FragmentActivity.viewModel(crossinline initializer: () -> T): T {
    return ViewModelProviders.of(this, vmFactory(initializer)).get(T::class.java)
}

inline fun <reified T : ViewModel> ViewModelStoreOwner.viewModel(crossinline initializer: () -> T): T {
    return ViewModelProvider(this, vmFactory(initializer)).get(T::class.java)
}

inline fun <reified T : ViewModel> FragmentActivity.viewModel(
    tag: String,
    crossinline initializer: () -> T
): T {
    return ViewModelProviders.of(this, vmFactory(initializer)).get(tag, T::class.java)
}

inline fun <reified T : ViewModel> Fragment.viewModel(crossinline initializer: () -> T): T {
    return ViewModelProviders.of(this, vmFactory(initializer)).get(T::class.java)
}

inline fun <reified T : ViewModel> Fragment.viewModel(
    tag: String,
    crossinline initializer: () -> T
): T {
    return ViewModelProviders.of(this, vmFactory(initializer)).get(tag, T::class.java)
}

@Suppress("UNCHECKED_CAST")
inline fun <VM : ViewModel> vmFactory(crossinline initializer: () -> VM) =
    object : ViewModelProvider.NewInstanceFactory() {
        override fun <T : ViewModel> create(modelClass: Class<T>): T = initializer() as T
    }