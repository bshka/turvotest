package com.itechart.turvotest.common.binding

import android.view.View
import androidx.databinding.BindingConversion
import com.itechart.turvotest.common.utils.Listener

@BindingConversion
fun convertLambdaToClickListener(listener: Listener?): View.OnClickListener = View.OnClickListener {
    listener?.invoke()
}