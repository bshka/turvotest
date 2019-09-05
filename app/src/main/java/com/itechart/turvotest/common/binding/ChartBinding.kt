package com.itechart.turvotest.common.binding

import androidx.databinding.BindingAdapter
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