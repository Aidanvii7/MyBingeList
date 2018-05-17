package com.aidanvii.mybingelist.core.bindingadapters

import android.databinding.BindingAdapter
import android.databinding.InverseBindingAdapter
import android.databinding.InverseBindingListener
import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.aidanvii.mybingelist.R
import com.aidanvii.toolbox.databinding.trackInstance
import android.R as AR


interface CharSequenceBindingConsumer {
    fun invoke(value: CharSequence)
}


internal var TextView._text: CharSequence
    @InverseBindingAdapter(attribute = "android:text", event = "android:textAttrChanged")
    get() = text
    @BindingAdapter("android:text")
    set(value) {
        if (text.toString() != value.toString()) {
            text = value
        }
    }

@BindingAdapter(
    "android:onTextChanged",
    "android:textAttrChanged", requireAll = false
)
internal fun TextView.bind(
    onTextChanged: CharSequenceBindingConsumer?,
    textAttrChanged: InverseBindingListener?
) {
    trackInstance(
        instanceResId = R.id.text_watcher,
        newInstance = if (onTextChanged != null || textAttrChanged != null) {
            object : TextWatcher {
                override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
                override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
                    onTextChanged?.invoke(s)
                    textAttrChanged?.onChange()
                }
                override fun afterTextChanged(s: Editable?) {}
            }
        } else null,
        onDetached = { removeTextChangedListener(it) },
        onAttached = { addTextChangedListener(it) }
    )
}
