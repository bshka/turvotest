package com.itechart.turvotest.screens.details

import android.content.Context
import android.graphics.Color
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.common.utils.applyDP
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.screens.common.ChartData
import com.itechart.turvotest.screens.common.viewmodel.BaseActionsViewModel
import com.itechart.turvotest.screens.common.viewmodel.ViewModelActions
import java.text.SimpleDateFormat
import java.util.*

class DetailsViewModel(
    ticker: Ticker,
    context: Context
) : BaseActionsViewModel<DetailsViewModelActions>() {

    val title = ticker.title
    val price = String.format("%.2f", ticker.lastPrice)

    val close: Listener = { sendEvent(DetailsViewModelActions.Close) }


    val chartData: ChartData? by lazy {
        if (ticker.history.isNullOrEmpty()) {
            null
        } else {
            val dataSets = mutableListOf<ILineDataSet>()
            val result = splitHistory(ticker.history)
            val xLabels = mutableListOf<String>()
            var j = -1

            for (i in result.dataSets.indices) {
                val entries = mutableListOf<Entry>()
                result.dataSets[i].forEach {
                    val label = DATE_FORMAT.format(it.key.time)
                    if (!xLabels.contains(label)) {
                        xLabels.add(label)
                        j++
                    }
                    entries.add(
                        Entry(
                            j.toFloat(), it.value
                        )
                    )
                }
                dataSets.add(
                    LineDataSet(entries, null).apply {
                        lineWidth = context.applyDP(1)
                        circleRadius = context.applyDP(1)
                        val c = if (i == result.maxInd) {
                            Color.RED
                        } else {
                            Color.GRAY
                        }
                        setCircleColor(c)
                        color = c
                        mode = LineDataSet.Mode.CUBIC_BEZIER
                        valueTextSize = 8f
                    }
                )
            }

            ChartData(LineData(dataSets), xLabels)
        }
    }

    private fun splitHistory(map: Map<Date, Float>): SplitRes {
        val res = mutableListOf<Map<Date, Float>>()
        var diff = 0f
        val values = map.toSortedMap().values.toList()
        val keys = map.keys.toSortedSet().toList()
        var value = values[0]
        var startValue: Float = value

        var maxInd = 0

        var part = mutableMapOf<Date, Float>()
        res.add(part)

        var up: Boolean? = null

        for (i in 1 until values.size - 1) {
            part[keys[i - 1]] = value

            when {
                value - values[i] < 0 -> {
                    // goes up
                    if (up == false) {
                        startValue = value
                        part = mutableMapOf()
                        part[keys[i - 1]] = value
                        res.add(part)
                    }


                    up = true
                }
                value - values[i] >= 0 -> {

                    if (value - startValue > diff) {
                        maxInd = res.size - 1
                        diff = value - startValue
                    }
                    // down or same
                    if (up == true) {
                        startValue = value
                        part = mutableMapOf()
                        part[keys[i - 1]] = value
                        res.add(part)
                    }

                    up = false
                }
            }

            value = values[i]
        }
        part[keys.last()] = values.last()

        if (values.last() - startValue > diff) {
            maxInd = res.size - 1
        }

        return SplitRes(maxInd, res)
    }

    data class SplitRes(
        val maxInd: Int,
        val dataSets: List<Map<Date, Float>>
    )

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("d", Locale.ENGLISH)
    }


}

sealed class DetailsViewModelActions : ViewModelActions {
    object Close : DetailsViewModelActions()
}