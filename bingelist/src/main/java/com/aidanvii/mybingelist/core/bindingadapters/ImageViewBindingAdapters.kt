package com.aidanvii.mybingelist.core.bindingadapters

import android.databinding.BindingAdapter
import android.graphics.drawable.Drawable
import com.aidanvii.mybingelist.core.customviews.AspectRatioAppCompatImageView

interface ImageViewBindingAdapters {

    @BindingAdapter(
        "android:imageUrl",
        "android:placeHolder",
        "android:aspectRatio", requireAll = false
    )
    fun AspectRatioAppCompatImageView.bind(
        imageUrl: String?,
        placeHolder: Drawable?,
        aspectRatio: Float?
    )
}