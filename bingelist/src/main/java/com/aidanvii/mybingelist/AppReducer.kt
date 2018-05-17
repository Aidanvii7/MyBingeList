package com.aidanvii.mybingelist

import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.favourites.FavouritesReducer
import com.aidanvii.mybingelist.genres.GenresReducer
import com.aidanvii.mybingelist.search.SearchReducer
import com.aidanvii.toolbox.redux.Store

class AppReducer(
    private val genresReducer: GenresReducer,
    private val searchReducer: SearchReducer,
    private val favouritesReducer: FavouritesReducer
) : Store.Reducer<AppAction, AppState> {

    override fun reduce(
        state: AppState,
        action: AppAction
    ) = when (action) {
        AppAction.Default -> AppState.DEFAULT
        is AppAction.GenresAction -> handleGenresAction(state, action)
        is AppAction.SearchAction -> handleSearchAction(state, action)
        is AppAction.FavouritesAction -> handleFavouritesAction(state, action)
    }

    private fun handleGenresAction(state: AppState, action: AppAction.GenresAction) =
        genresReducer.reduce(state.genres, action).let { newSearchState ->
            state.copy(genres = newSearchState)
        }

    private fun handleSearchAction(state: AppState, action: AppAction.SearchAction) =
        searchReducer.reduce(state.searchSection, action).let { newSearchState ->
            state.copy(searchSection = newSearchState)
        }

    private fun handleFavouritesAction(state: AppState, action: AppAction.FavouritesAction) =
        favouritesReducer.reduce(state.favouritesSection, action).let { newFavouritesState ->
            state.copy(
                searchSection = state.searchSection.copy(
                    movies = state.searchSection.movies.map { movie ->
                        movie.copy(isFavourite = true).let { favouriteMovie ->
                            if (newFavouritesState.movies.contains(favouriteMovie)) favouriteMovie else movie
                        }
                    }.toSet()
                ),
                favouritesSection = newFavouritesState
            )
        }
}