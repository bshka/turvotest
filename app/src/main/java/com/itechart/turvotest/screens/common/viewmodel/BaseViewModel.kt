package com.itechart.turvotest.screens.common.viewmodel

import androidx.annotation.CallSuper
import androidx.lifecycle.ViewModel
import com.itechart.turvotest.common.utils.safetySubscribe
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber

abstract class BaseViewModel : ViewModel() {

    protected val disposables = CompositeDisposable()

    @CallSuper
    open fun start() = Unit

    open fun resume() = Unit

    @CallSuper
    open fun pause() = Unit

    @CallSuper
    open fun stop() = Unit

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }

    protected fun <T> observe(what: Observable<T>, action: (T) -> Unit) {
        what.safetySubscribe(
            Consumer { result: T -> action(result) },
            Consumer { error -> Timber.e(error) }
        ).connectToLifecycle()
    }

    protected fun Disposable.connectToLifecycle() = disposables.add(this)

}