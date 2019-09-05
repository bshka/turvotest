package com.itechart.turvotest.screens.portfolio

import android.graphics.Color
import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.itechart.turvotest.R
import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.databinding.ItemPortfolioBinding
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.screens.common.ChartData
import com.itechart.turvotest.screens.common.list.ListItemActions
import com.itechart.turvotest.screens.common.list.ListItemView

class PortfolioListItemView(
    ticker: Ticker
) : ListItemView<ListItemActions>() {

    private var binding: ItemPortfolioBinding? = null

    val title = ticker.title
    val price = String.format("%.2f", ticker.lastPrice)
    val click: Listener = {
        binding?.let {
            it.title.transitionName = "title"
            it.price.transitionName = "price"
            it.chart.transitionName = "chart"
            sendEvent(PortfolioListItemActions.TickerClicked(ticker, it.title, it.price, it.chart))
        }
    }

    val chartData: ChartData? by lazy {
        if (ticker.history.isNullOrEmpty()) {
            null
        } else {
            val dataSets = mutableListOf<ILineDataSet>()
            val entries = mutableListOf<Entry>()
            var j = -1
            ticker.history.forEach {
                entries.add(
                    Entry(j++.toFloat(), it.value)
                )
            }
            dataSets.add(
                LineDataSet(entries, null).apply {
                    lineWidth = 3f
                    setDrawCircles(false)
                    color = Color.GRAY
                    mode = LineDataSet.Mode.CUBIC_BEZIER
                    setDrawValues(false)
                }
            )
            ChartData(LineData(dataSets), null, false)
        }
    }

    override val layout: Int = R.layout.item_portfolio
    override val id: Long = ticker.id

    override fun onBind(binding: ViewDataBinding) {
        super.onBind(binding)
        this.binding = binding as? ItemPortfolioBinding
    }

    override fun onUnbind(binding: ViewDataBinding) {
        super.onUnbind(binding)
        this.binding = null
    }

}

sealed class PortfolioListItemActions : ListItemActions {
    data class TickerClicked(
        val ticker: Ticker,
        val title: TextView,
        val price: TextView,
        val chart: LineChart
    ) :
        PortfolioListItemActions()
}