package com.itechart.turvotest.common.binding

import androidx.databinding.BindingAdapter
import com.google.android.material.textfield.TextInputLayout

object TextInputLayoutBinding {

    @BindingAdapter("errorString")
    @JvmStatic
    fun setError(textInputLayout: TextInputLayout, error: String?) {
        textInputLayout.error = error
    }

}