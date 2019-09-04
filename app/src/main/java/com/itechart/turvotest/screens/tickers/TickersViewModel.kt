package com.itechart.turvotest.screens.tickers

import android.content.Context
import android.text.Editable
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import com.itechart.turvotest.R
import com.itechart.turvotest.common.utils.Listener
import com.itechart.turvotest.screens.common.viewmodel.BaseActionsViewModel
import com.itechart.turvotest.screens.common.viewmodel.ViewModelActions

class TickersViewModel(
    private val context: Context
) : BaseActionsViewModel<TickerViewModelActions>() {

    val next: Listener = { sendEvent(TickerViewModelActions.NextClicked(tickersList(text))) }

    val nextEnabled = ObservableBoolean(false)
    val error = ObservableField<String?>()

    var text: String? = ""
        private set

    fun setText(editable: Editable?) {
        text = editable?.toString()
        val tickers = tickersList(text)
        val errorText = getInputError(tickers)
        nextEnabled.set(errorText.isNullOrEmpty())
        error.set(errorText)
    }

    private fun getInputError(tickers: List<String>): String? =
        when {
            tickers.isEmpty() -> context.getString(R.string.tickers_error_enter_tickers)
            tickers.size > 5 -> context.getString(R.string.tickers_error_too_much_tickers)
            else -> null
        }

    private fun tickersList(tickers: String?): List<String> =
        if (tickers.isNullOrEmpty()) {
            emptyList()
        } else {
            tickers.trim().split(",").map { it.trim() }.filter { it.isNotEmpty() }
        }

}

sealed class TickerViewModelActions : ViewModelActions {
    data class NextClicked(val tickers: List<String>) : TickerViewModelActions()
}