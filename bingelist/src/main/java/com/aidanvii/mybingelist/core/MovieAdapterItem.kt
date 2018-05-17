package com.aidanvii.mybingelist.core

import com.aidanvii.mybingelist.R
import com.aidanvii.mybingelist.core.entities.Movie
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.toolbox.adapterviews.databinding.BindableAdapterItem
import com.aidanvii.toolbox.redux.Store

data class MovieAdapterItem(
    private val movie: Movie,
    private val store: Store<AppAction, AppState>
) : BindableAdapterItem.Base() {

    override val layoutId: Int get() = R.layout.card_movie

    override val lazyBindableItem = lazy { MovieViewModel(movie, store) }
}