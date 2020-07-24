package com.appspell.android.templates.mvvm.base

import androidx.lifecycle.*
import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicBoolean


fun <X, Y> LiveData<X>.map(func: (source: X) -> Y) = Transformations.map(this, func)

fun <X, Y> LiveData<X>.switchMap(func: (source: X?) -> LiveData<Y>) =
    Transformations.switchMap(this, func)

fun <X, Y, Z> LiveData<X>.zip(
    stream: LiveData<Y>,
    func: (source1: X?, source2: Y?) -> Z
): LiveData<Z> {
    val result = MediatorLiveData<Z>()
    result.addSource(this, { x -> result.setValue(func.invoke(x, stream.value)) })
    result.addSource(stream, { y -> result.setValue(func.invoke(this.value, y)) })
    return result
}

fun <X> LiveData<X>.merge(stream: LiveData<X>): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this, { x -> result.setValue(x) })
    result.addSource(stream, { x -> result.setValue(x) })
    return result
}

fun <X, Y> LiveData<X>.mergeIgnoringType(stream: LiveData<Y>): LiveData<Any?> {
    val result = MediatorLiveData<Any?>()
    result.addSource(this) { x -> result.value = x }
    result.addSource(stream) { y -> result.value = y }
    return result
}

fun <X, Y> LiveData<X>.mapNotNull(func: (source: X?) -> Y?): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this, { x ->
        val y = func.invoke(x)
        if (y != null) result.setValue(y)
    })
    return result
}

fun <X> LiveData<X>.filter(func: (source: X?) -> Boolean): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this, { x -> if (func.invoke(x)) result.setValue(x) })
    return result
}

fun <X> LiveData<X>.filterNotNull(func: (source: X) -> Boolean): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this, { x ->
        if (x != null && func.invoke(x)) result.setValue(x)
    })
    return result
}

fun <X> LiveData<X>.doOnNext(func: (source: X) -> Unit): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        func.invoke(x)
        result.value = x
    }
    return result
}

fun <X> LiveData<X>.doAfterNext(func: (source: X) -> Unit): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this) { x ->
        result.value = x
        func.invoke(x)
    }
    return result
}

fun <M, S> MediatorLiveData<M>.observeOnce(source: LiveData<S>, func: (data: S?) -> Unit) {
    this.removeSource(source)
    this.addSource(source) {
        func.invoke(it)
        this@observeOnce.removeSource(source)
    }
}

fun <T> LiveData<T>.observeOnce(func: (data: T?) -> Unit) {
    observeForever(object : Observer<T> {
        override fun onChanged(t: T?) {
            func.invoke(t)
            removeObserver(this)
        }
    })
}

fun <T> LiveData<T>.observeOnce(owner: LifecycleOwner, func: (data: T?) -> Unit) {
    observe(owner, object : Observer<T> {
        override fun onChanged(t: T?) {
            func.invoke(t)
            removeObserver(this)
        }
    })
}

fun <X, Y> LiveData<X>.flatMap(func: (source: X) -> LiveData<Y>): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this) { x ->
        val newSource = func.invoke(x)
        result.removeSource(newSource)
        result.addSource(newSource) { y ->
            result.value = y
        }
    }
    return result
}

inline fun <T : Any> LiveData<T>.observe(
    owner: LifecycleOwner,
    crossinline onObserve: (T) -> Unit
): Observer<T> =
    Observer<T> { onObserve(it!!) }.also { observe(owner, it) }

fun <T> LiveData<T?>.observeFirstNotNull(func: (T) -> Unit) {
    observeForever(object : Observer<T?> {
        override fun onChanged(t: T?) {
            t?.let {
                func.invoke(it)
                this@observeFirstNotNull.removeObserver(this)
            }
        }
    })
}

fun <X, T, Z> LiveData<X>.combine(
    second: LiveData<T>,
    combineFunction: (X?, T?) -> Z
): LiveData<Z> {
    val result: MediatorLiveData<Z> = MediatorLiveData()

    val firstEmitted = AtomicBoolean(false)
    var firstValue: X? = null

    val secondEmitted = AtomicBoolean(false)
    var secondValue: T? = null
    result.addSource(this) { value ->
        firstValue = value
        firstEmitted.set(true)
        if (firstEmitted.get() && secondEmitted.get()) {
            result.value = combineFunction(firstValue, secondValue)
        }
    }
    result.addSource(second) { value ->
        secondValue = value
        secondEmitted.set(true)
        if (firstEmitted.get() && secondEmitted.get()) {
            result.value = combineFunction(firstValue, secondValue)
        }
    }
    return result
}

fun <T> LiveData<T>.distinct(): LiveData<T> {
    val result: MediatorLiveData<T> = MediatorLiveData()
    result.addSource(this) {
        if (it != result.value) {
            result.value = it
        }
    }
    return result
}

fun <X> LiveData<X?>.filterOutNull(): LiveData<X> {
    val result = MediatorLiveData<X>()
    result.addSource(this, { x ->
        if (x != null) result.setValue(x)
    })
    return result
}

fun <T> LiveData<T>.asMutableLiveData() = (this as? MutableLiveData<T>)

fun <T> MutableLiveData<T>.defaultValue(default: T): MutableLiveData<T> {
    if (value == null) {
        value = default
    }
    return this
}


/**
 * Only during first call we need to wait till values from both streams (LiveData<X>,LiveData<Y>) will be received,
 * to prevent receiving in Ui observer useless data
 */
fun <X, Y, Z> LiveData<X>.zipBackground(
    stream: LiveData<Y>,
    func: (source1: X?, source2: Y?) -> Z
): LiveData<Z> {
    val result = MediatorLiveData<Z>()
    var job: Job? = null

    var waitX: X? = null
    var waitY: Y? = null

    result.addSource(this) { x ->
        job?.cancel()
        waitX = x
        job = result.check(waitX, waitY, func)
    }
    result.addSource(stream) { y ->
        job?.cancel()
        waitY = y
        job = result.check(waitX, waitY, func)
    }
    return result
}

private fun <X, Y, Z> MediatorLiveData<Z>.check(
    waitX: X?,
    waitY: Y?,
    func: (source1: X?, source2: Y?) -> Z
): Job? {
    return GlobalScope.launch(Dispatchers.IO) {
        val mappedResult = func.invoke(waitX, waitY)
        withContext(Dispatchers.Main) {
            this@check.value = mappedResult
        }
    }
}

fun <X, Y> LiveData<X>.mapBackground(func: (source: X) -> Y): LiveData<Y> {
    val result = MediatorLiveData<Y>()
    result.addSource(this@mapBackground) {
        GlobalScope.launch(Dispatchers.IO) {
            val mappedResult = func.invoke(it)
            withContext(Dispatchers.Main) {
                result.value = mappedResult
            }
        }
    }
    return result
}