package com.itechart.turvotest.common.binding

import androidx.databinding.ObservableField
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject

open class ObservableValue<T>(defaultVal: T?) : ObservableField<T>(defaultVal) {

    private val subjectLazyDelegate = lazy { BehaviorSubject.createDefault(createEmittingValue()) }
    private val subject by subjectLazyDelegate
    val rxObservable: Observable<ValueHolder<T?>> get() = subject

    var value: T?
        get() = get()
        set(value) = set(value)

    override fun get(): T? = super.get()

    override fun set(value: T?) = super.set(value)

    override fun notifyChange() {
        super.notifyChange()
        if (subjectLazyDelegate.isInitialized()) {
            subject.onNext(createEmittingValue())
        }
    }

    private fun createEmittingValue(): ValueHolder<T?> = ValueHolder(value)
}


class ValueHolder<T>(val value: T)

class ObservableString(defaultVal: String? = null) : ObservableValue<String?>(defaultVal)