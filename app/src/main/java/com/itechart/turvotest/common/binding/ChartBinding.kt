package com.itechart.turvotest.common.binding

import android.graphics.Color
import androidx.databinding.BindingAdapter
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter
import com.itechart.turvotest.screens.common.ChartData

object ChartBinding {

    @BindingAdapter("chartData")
    @JvmStatic
    fun setData(chart: LineChart, data: ChartData?) {
        data?.let {
            chart.setBackgroundColor(Color.TRANSPARENT)
            chart.description.isEnabled = false
            chart.data = data.lineData
            chart.setTouchEnabled(false)
            chart.setPinchZoom(false)
            chart.isDragEnabled = false
            chart.description.isEnabled = false
            chart.legend.isEnabled = false
            if (!data.showAxis) {
                chart.axisLeft.isEnabled = false
                chart.axisRight.isEnabled = false
                chart.xAxis.isEnabled = false
                chart.xAxis.axisLineColor = Color.TRANSPARENT
            } else {
                chart.xAxis.valueFormatter = IndexAxisValueFormatter(data.xAxisLabels)
            }
        }
    }

}