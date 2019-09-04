package com.itechart.turvotest.common.utils

import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber

operator fun CompositeDisposable.plus(disposable: Disposable) {
    add(disposable)
}

fun <T> Observable<T>.safetySubscribe(
        onSuccess: Consumer<in T>,
        onError: Consumer<in Throwable> = Consumer { error: Throwable ->
            Timber.e("RxUtils onError <T> Single<T>.safetySubscribe: $error")
        }
): Disposable =
        doOnNext(onSuccess)
                .doOnError(onError)
                .ignoreElements()
                .safetySubscribe()

fun Completable.safetySubscribe(): Disposable =
        onErrorComplete()
                .subscribe()