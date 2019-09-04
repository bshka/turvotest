package com.itechart.turvotest.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Ticker(
    val id: Long,
    val title: String,
    val price: Float
) : Parcelable