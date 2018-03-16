package com.appspell.android.templates.mvvm.base

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.Transformations

fun <X, Y> LiveData<X>.map(func: (source: X) -> Y) = Transformations.map(this, func)