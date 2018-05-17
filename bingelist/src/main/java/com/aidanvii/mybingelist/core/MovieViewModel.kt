package com.aidanvii.mybingelist.core

import com.aidanvii.mybingelist.core.entities.Movie
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.toolbox.databinding.ObservableViewModel
import com.aidanvii.toolbox.redux.Store

class MovieViewModel(
    private val movie: Movie,
    private val store: Store<AppAction, AppState>
) : ObservableViewModel() {

    val imageUrl: String? get() = movie.posterImage

    val rating: Double get() = movie.rating

    val isFavourite: Boolean get() = movie.isFavourite

    fun onBookmarkClicked() {
        store.dispatch(
            action = if (isFavourite) {
                AppAction.FavouritesAction.RemoveFromFavourites(movie)
            } else {
                AppAction.FavouritesAction.AddToFavourites(movie)
            }
        )
    }
}