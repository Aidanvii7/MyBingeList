package com.aidanvii.mybingelist.favourites

import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.AddToFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.AddToFavouritesFailed
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.AddToFavouritesSucceeded
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavouritesFailed
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavouritesSucceeded
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.RemoveFromFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.RemoveFromFavouritesFailed
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.RemoveFromFavouritesSucceeded
import com.aidanvii.mybingelist.core.redux.AppState.MoviesSection.Favourites
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.toolbox.redux.Store

class FavouritesReducer :
    Store.Reducer<FavouritesAction, Favourites> {

    override fun reduce(
        state: Favourites,
        action: FavouritesAction
    ) = when (action) {
        is LoadFavourites -> handleLoadFavourites(state)
        is LoadFavouritesSucceeded -> handleLoadFavouritesSucceeded(state, action)
        is LoadFavouritesFailed -> handleLoadFavouritesFailed(state, action)
        is AddToFavourites -> handleAddToFavourites(state)
        is AddToFavouritesSucceeded -> handleAddToFavouritesSucceeded(state, action)
        is AddToFavouritesFailed -> handleAddToFavouritesFailed(state)
        is RemoveFromFavourites -> handleRemoveFromFavourites(state)
        is RemoveFromFavouritesSucceeded -> handleRemoveFromFavouritesSucceeded(state, action)
        is RemoveFromFavouritesFailed -> handleRemoveFromFavouritesFailed(state)
    }

    private fun handleLoadFavourites(favouritesState: Favourites) = favouritesState.copy(
        movies = emptySet(),
        loadingState = LoadingState.Loading
    )

    private fun handleLoadFavouritesSucceeded(
        favouritesState: Favourites,
        action: LoadFavouritesSucceeded
    ) = favouritesState.copy(
        movies = action.movies.map { it.copy(isFavourite = true) }.toSet(),
        loadingState = LoadingState.Loaded
    )

    private fun handleLoadFavouritesFailed(
        favouritesState: Favourites,
        action: LoadFavouritesFailed
    ) = favouritesState.copy(
        movies = emptySet(),
        loadingState = LoadingState.NotLoaded(
            error = action.error
        )
    )

    private fun handleAddToFavourites(favouritesState: Favourites) = favouritesState.copy(
        loadingState = LoadingState.Loading
    )

    private fun handleAddToFavouritesSucceeded(
        favouritesState: Favourites,
        action: AddToFavouritesSucceeded
    ) = favouritesState.copy(
        movies = favouritesState.movies.plus(action.movie.copy(isFavourite = true)),
        loadingState = LoadingState.Loaded
    )

    private fun handleAddToFavouritesFailed(
        favouritesState: Favourites
    ) = favouritesState.copy(
        movies = favouritesState.movies,
        loadingState = LoadingState.Loaded
    )

    private fun handleRemoveFromFavourites(
        favouritesState: Favourites
    ) = favouritesState.copy(
        loadingState = LoadingState.Loading
    )

    private fun handleRemoveFromFavouritesSucceeded(
        favouritesState: Favourites,
        action: RemoveFromFavouritesSucceeded
    ) = favouritesState.copy(
        movies = favouritesState.movies.minus(action.movie.copy(isFavourite = true)),
        loadingState = LoadingState.Loaded
    )

    private fun handleRemoveFromFavouritesFailed(
        favouritesState: Favourites
    ) = favouritesState.copy(
        movies = favouritesState.movies,
        loadingState = LoadingState.Loaded
    )
}