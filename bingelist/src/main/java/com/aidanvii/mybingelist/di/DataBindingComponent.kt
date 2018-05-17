package com.aidanvii.mybingelist.di

import android.databinding.DataBindingComponent
import android.databinding.DataBindingUtil
import com.aidanvii.mybingelist.core.bindingadapters.GlideImageViewBindingAdapters

class DataBindingComponent : DataBindingComponent {

    fun makeDefaultComponent() {
        DataBindingUtil.setDefaultComponent(this)
    }
    private val imageViewBindingAdapters =
        GlideImageViewBindingAdapters()

    override fun getImageViewBindingAdapters() = imageViewBindingAdapters
}