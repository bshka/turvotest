package com.itechart.turvotest.screens.portfolio

import android.content.Context
import android.widget.TextView
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.mikephil.charting.charts.LineChart
import com.itechart.turvotest.common.SchedulersProvider
import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.network.LoadTickerHistoryUseCase
import com.itechart.turvotest.screens.common.list.ListItemActions
import com.itechart.turvotest.screens.common.viewmodel.BaseActionsViewModel
import com.itechart.turvotest.screens.common.viewmodel.ViewModelActions
import io.reactivex.subjects.PublishSubject
import timber.log.Timber

class PortfolioViewModel(
    private val tickers: Array<String>,
    private val loadTicker: LoadTickerHistoryUseCase,
    private val schedulersProvider: SchedulersProvider,
    context: Context
) : BaseActionsViewModel<PortfolioViewModelActions>() {

    val listEventsObserver = PublishSubject.create<ListItemActions>()
    val dataList = ObservableField<List<PortfolioListItemView>>()
    val childToShow = ObservableInt(0)

    val close: Listener = { sendEvent(PortfolioViewModelActions.Close) }
    val decoration = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)

    init {
        registerActionsSource(listEventsObserver.map {
            when (it) {
                is PortfolioListItemActions.TickerClicked -> PortfolioViewModelActions.TickerClicked(
                    it.ticker, it.title, it.price, it.chart
                )
                else -> throw IllegalArgumentException("Unknown item -> $it")
            }
        })
        loadTickers()
    }

    private fun loadTickers() {
        childToShow.set(2)
        loadTicker.load(tickers)
            .map {
                it.map { ticker ->
                    PortfolioListItemView(ticker)
                }
            }
            .observeOn(schedulersProvider.main())
            .subscribe(
                {
                    childToShow.set(0)
                    dataList.set(it)
                },
                {
                    childToShow.set(1)
                    Timber.e(it)
                }
            ).connectToLifecycle()
    }

    fun refresh() {
        loadTickers()
    }

}

sealed class PortfolioViewModelActions : ViewModelActions {
    data class TickerClicked(
        val ticker: Ticker,
        val title: TextView,
        val price: TextView,
        val chart: LineChart
    ) : PortfolioViewModelActions()

    object Close : PortfolioViewModelActions()
}