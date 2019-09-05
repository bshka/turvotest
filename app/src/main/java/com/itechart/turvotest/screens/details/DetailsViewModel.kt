package com.itechart.turvotest.screens.details

import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.model.Ticker
import com.itechart.turvotest.screens.common.viewmodel.BaseActionsViewModel
import com.itechart.turvotest.screens.common.viewmodel.ViewModelActions
import java.text.SimpleDateFormat
import java.util.*

class DetailsViewModel(
    ticker: Ticker
) : BaseActionsViewModel<DetailsViewModelActions>() {

    val title = ticker.title
    val price = String.format("%.2f", ticker.lastPrice)

    val close: Listener = { sendEvent(DetailsViewModelActions.Close) }


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

    companion object {
        private val DATE_FORMAT = SimpleDateFormat("d", Locale.ENGLISH)
    }


}

sealed class DetailsViewModelActions : ViewModelActions {
    object Close : DetailsViewModelActions()
}