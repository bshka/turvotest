package com.itechart.turvotest.screens.common

import com.github.mikephil.charting.data.LineData

data class ChartData(
    val lineData: LineData,
    val xAxisLabels: List<String>? = null,
    val showAxis: Boolean = true
)