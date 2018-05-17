package com.aidanvii.mybingelist.core.bindingadapters

import android.databinding.BindingAdapter
import android.view.View
import com.aidanvii.toolbox.databinding.BindingAction

@BindingAdapter("android:onClick")
fun View.onClick(onClick: BindingAction?) {
    setOnClickListener(
        onClick?.let {
            View.OnClickListener {
                onClick.invoke()
            }
        }
    )
}