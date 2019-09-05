package com.itechart.turvotest.common.utils

import android.content.Context
import androidx.core.content.edit
import androidx.preference.PreferenceManager

class PreferenceHelper(private val context: Context) {

    var savedTickers: String?
        get() = PreferenceManager.getDefaultSharedPreferences(context).getString("tickers", null)
        set(value) {
            PreferenceManager.getDefaultSharedPreferences(context).edit {
                putString("tickers", value)
            }
        }

}