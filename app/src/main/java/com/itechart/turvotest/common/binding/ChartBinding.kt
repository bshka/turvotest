package com.itechart.turvotest.common.binding

import androidx.databinding.BindingAdapter
import com.anychart.AnyChart
import com.anychart.AnyChartView
import com.anychart.chart.common.dataentry.DataEntry
import com.db.williamchart.view.LineChartView

object ChartBinding {

    @BindingAdapter("chartData")
    @JvmStatic
    fun setData(chart: LineChartView, data: LinkedHashMap<String, Float>?) {
        data?.let {
            chart.show(data)
        }
    }

}