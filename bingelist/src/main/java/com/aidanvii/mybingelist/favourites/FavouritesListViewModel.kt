package com.aidanvii.mybingelist.favourites

import android.databinding.Bindable
import com.aidanvii.mybingelist.core.MovieAdapterItem
import com.aidanvii.mybingelist.core.MovieListViewModel
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.toolbox.redux.Store
import com.aidanvii.toolbox.rxutils.RxSchedulers

class FavouritesListViewModel(
    store: Store<AppAction, AppState>,
    rxSchedulers: RxSchedulers
) : MovieListViewModel(store, rxSchedulers) {

    @get:Bindable("lastKnownState")
    val showLoader: Boolean
        get() = lastKnownState?.run { favouritesSection.loadingState == LoadingState.Loading } ?: false

    fun refresh() {
        store.dispatch(AppAction.FavouritesAction.LoadFavourites)
    }

    override fun toMovieAdapterItems(state: AppState): List<MovieAdapterItem> {
        return state.favouritesSection.movies.map { movie -> MovieAdapterItem(movie, store) }
    }
}