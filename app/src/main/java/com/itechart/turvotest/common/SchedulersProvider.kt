package com.itechart.turvotest.common

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class SchedulersProvider {
    fun computation(): Scheduler = Schedulers.computation()
    fun io(): Scheduler = Schedulers.io()
    fun main(): Scheduler = AndroidSchedulers.mainThread()
}