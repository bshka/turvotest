package com.itechart.turvotest.common.binding

import android.view.View
import androidx.appcompat.widget.Toolbar
import androidx.databinding.BindingAdapter

object ToolbarBinding {

    @BindingAdapter("navigationClick")
    @JvmStatic
    fun navigationClick(toolbar: Toolbar, listener: View.OnClickListener?) {
        toolbar.setNavigationOnClickListener(listener)
    }


}