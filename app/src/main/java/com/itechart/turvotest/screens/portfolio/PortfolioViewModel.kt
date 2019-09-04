package com.itechart.turvotest.screens.portfolio

import androidx.databinding.ObservableField
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.screens.common.list.ListItemActions
import com.itechart.turvotest.screens.common.viewmodel.BaseActionsViewModel
import com.itechart.turvotest.screens.common.viewmodel.ViewModelActions
import io.reactivex.subjects.PublishSubject

class PortfolioViewModel : BaseActionsViewModel<PortfolioViewModelActions>() {

    val listEventsObserver = PublishSubject.create<ListItemActions>()
    val dataList = ObservableField<List<PortfolioListItemView>>()

    init {
        registerActionsSource(listEventsObserver.map {
            when (it) {
                is PortfolioListItemActions.TickerClicked -> PortfolioViewModelActions.TickerClicked(it.ticker)
                else -> throw IllegalArgumentException("Unknown item -> $it")
            }
        })
    }

}

sealed class PortfolioViewModelActions : ViewModelActions {
    data class TickerClicked(val ticker: Ticker): PortfolioViewModelActions()
}