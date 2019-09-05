package com.itechart.turvotest.common.binding

import android.widget.ViewFlipper
import androidx.databinding.BindingAdapter

object ViewSwitcherBinding {

    @BindingAdapter("childToDisplay")
    @JvmStatic
    fun childToDisplay(viewSwitcher: ViewFlipper, child: Int?) {
        child?.let {
            viewSwitcher.displayedChild = it
        }
    }

}