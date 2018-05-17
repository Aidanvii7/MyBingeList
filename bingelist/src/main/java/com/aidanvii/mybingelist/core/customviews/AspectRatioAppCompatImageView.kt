package com.aidanvii.mybingelist.core.customviews

import android.content.Context
import android.support.v7.widget.AppCompatImageView
import android.util.AttributeSet
import android.view.View

class AspectRatioAppCompatImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : AppCompatImageView(context, attrs, defStyleAttr) {

    private var aspectRatio = 1f

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = View.MeasureSpec.getSize(widthMeasureSpec)
        val height = (width / aspectRatio).toInt()
        setMeasuredDimension(width, height)
    }

    fun setAspectRatio(aspectRatio: Float) {
        this.aspectRatio = aspectRatio
    }
}