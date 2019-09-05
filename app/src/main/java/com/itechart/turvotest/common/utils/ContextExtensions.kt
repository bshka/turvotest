package com.itechart.turvotest.common.utils

import android.content.Context
import android.util.TypedValue

fun Context.applyDP(px: Float): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px, resources.displayMetrics)

fun Context.applyDP(px: Int): Float =
        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, px.toFloat(), resources.displayMetrics)