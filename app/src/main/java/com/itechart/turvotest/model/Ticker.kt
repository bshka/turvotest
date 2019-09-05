package com.itechart.turvotest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize
import java.util.*

@Parcelize
data class Ticker(
    val id: Long,
    val title: String,
    val lastPrice: Float,
    val history: Map<Date, Float>?
) : Parcelable