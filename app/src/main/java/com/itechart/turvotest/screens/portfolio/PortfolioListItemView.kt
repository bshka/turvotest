package com.itechart.turvotest.screens.portfolio

import com.itechart.turvotest.R
import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.screens.common.list.ListItemActions
import com.itechart.turvotest.screens.common.list.ListItemView

class PortfolioListItemView(
    ticker: Ticker
) : ListItemView<ListItemActions>() {

    val title = ticker.title
    val price = String.format("%.2f", ticker.price)
    val click: Listener = { sendEvent(PortfolioListItemActions.TickerClicked(ticker)) }

    override val layout: Int = R.layout.item_portfolio
    override val id: Long = ticker.id


}

sealed class PortfolioListItemActions : ListItemActions {
    data class TickerClicked(val ticker: Ticker) : PortfolioListItemActions()
}