package com.itechart.turvotest.screens.portfolio

import android.widget.TextView
import androidx.databinding.ViewDataBinding
import com.db.williamchart.view.LineChartView
import com.itechart.turvotest.R
import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.databinding.ItemPortfolioBinding
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.screens.common.list.ListItemActions
import com.itechart.turvotest.screens.common.list.ListItemView
import java.text.SimpleDateFormat
import java.util.*

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

//    val chartData: List<DataEntry> by lazy {
//        val list = mutableListOf<DataEntry>()
//        ticker.history.forEach {
//            list.add(
//                ValueDataEntry(DATE_FORMAT.format(it.key), it.value)
//            )
//        }
//        list
//    }

    val chartData: LinkedHashMap<String, Float>? by lazy {
        if (ticker.history.isNullOrEmpty()) {
            null
        } else {
            val list = linkedMapOf<String, Float>()
            ticker.history.forEach {
                list[DATE_FORMAT.format(it.key)] = it.value
            }
            list
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

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("d", Locale.ENGLISH)
    }


}

sealed class PortfolioListItemActions : ListItemActions {
    data class TickerClicked(
        val ticker: Ticker,
        val title: TextView,
        val price: TextView,
        val chart: LineChartView
    ) :
        PortfolioListItemActions()
}