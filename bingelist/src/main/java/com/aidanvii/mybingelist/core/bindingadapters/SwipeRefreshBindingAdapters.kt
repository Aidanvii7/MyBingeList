package com.aidanvii.mybingelist.core.bindingadapters

import android.databinding.BindingAdapter
import android.support.v4.widget.SwipeRefreshLayout

@BindingAdapter("android:onRefresh")
fun SwipeRefreshLayout.bind(onRefreshListener: SwipeRefreshLayout.OnRefreshListener?) {
    setOnRefreshListener(onRefreshListener)
}

@BindingAdapter("android:refreshing")
fun SwipeRefreshLayout.bind(refreshing: Boolean?) {
    refreshing?.let { isRefreshing = it }
}