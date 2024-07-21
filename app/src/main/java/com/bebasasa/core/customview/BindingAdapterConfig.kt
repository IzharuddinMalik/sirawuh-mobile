package com.bebasasa.core.customview

import android.view.View
import androidx.databinding.BindingAdapter

@BindingAdapter("visibility")
fun isVisible(view: View, isVisible: Boolean) {
    if (isVisible) view.visibility = View.VISIBLE else view.visibility = View.GONE
}