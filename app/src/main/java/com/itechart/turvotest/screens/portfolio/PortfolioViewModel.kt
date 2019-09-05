package com.itechart.turvotest.screens.portfolio

import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import com.itechart.turvotest.common.SchedulersProvider
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
    private val schedulersProvider: SchedulersProvider
) : BaseActionsViewModel<PortfolioViewModelActions>() {

    val listEventsObserver = PublishSubject.create<ListItemActions>()
    val dataList = ObservableField<List<PortfolioListItemView>>()
    val childToShow = ObservableInt(0)

    init {
        registerActionsSource(listEventsObserver.map {
            when (it) {
                is PortfolioListItemActions.TickerClicked -> PortfolioViewModelActions.TickerClicked(
                    it.ticker
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
    data class TickerClicked(val ticker: Ticker) : PortfolioViewModelActions()
}