package com.aidanvii.mybingelist.core.bindingadapters

import android.graphics.drawable.Drawable
import android.widget.ImageView
import com.aidanvii.mybingelist.core.customviews.AspectRatioAppCompatImageView
import com.bumptech.glide.DrawableRequestBuilder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

class GlideImageViewBindingAdapters :
    ImageViewBindingAdapters {

    override fun AspectRatioAppCompatImageView.bind(
        imageUrl: String?,
        placeHolder: Drawable?,
        aspectRatio: Float?
    ) {
        aspectRatio?.let { setAspectRatio(aspectRatio) }
        glideWith(
            imageUrl = imageUrl,
            placeHolder = placeHolder,
            then = { dontTransform() }
        )
    }

    private inline fun ImageView.glideWith(
        imageUrl: String?,
        placeHolder: Drawable? = null,
        then: DrawableRequestBuilder<String>.() -> DrawableRequestBuilder<String>
    ) {
        if (!imageUrl.isNullOrEmpty()) {
            Glide.with(context)
                .load(imageUrl)
                .placeholder(placeHolder)
                .then()
                .diskCacheStrategy(DiskCacheStrategy.SOURCE)
                .into(this)
        } else {
            setImageDrawable(placeHolder)
        }
    }
}