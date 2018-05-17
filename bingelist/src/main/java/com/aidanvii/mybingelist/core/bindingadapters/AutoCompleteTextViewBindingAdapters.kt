package com.aidanvii.mybingelist.core.bindingadapters

import android.databinding.BindingAdapter
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import com.aidanvii.mybingelist.R
import com.aidanvii.toolbox.databinding.trackValue
import android.R as AR


@BindingAdapter("android:suggestions")
fun AutoCompleteTextView.bind(suggestions: Set<String>?) {
    trackValue(
        valueResId = R.id.autocomplete_suggestions,
        newValue = suggestions,
        onNewValue = { bind(it.toList()) },
        onOldValue = { bind(emptyList()) }
    )
}

@BindingAdapter("android:showSuggestionsOnLongClick")
fun AutoCompleteTextView.bind(showSuggestionsOnFocus: Boolean) {
    setOnLongClickListener(
        if (showSuggestionsOnFocus) {
            View.OnLongClickListener { _ ->
                showDropDown();true
            }
        } else null
    )
}

private fun AutoCompleteTextView.bind(suggestionsList: List<String>) {
    ArrayAdapter<String>(
        context,
        R.layout.suggesstion,
        suggestionsList
    ).let { adapter ->
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        setAdapter(adapter)
        showDropDown()
    }
}
