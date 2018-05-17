package com.aidanvii.mybingelist.core.redux

import com.aidanvii.mybingelist.core.entities.Movie

sealed class AppAction {

    object Default : AppAction()

    sealed class GenresAction : AppAction() {

        object LoadGenres : GenresAction()

        data class LoadGenresSucceeded(
            val genres: Set<String>
        ) : GenresAction()

        data class LoadGenresFailed(
            val error: Throwable
        ) : GenresAction()
    }

    sealed class SearchAction : AppAction() {

        data class LoadSearch(
            val searchQuery: String
        ) : SearchAction()

        data class LoadSearchResultsSucceeded(
            val searchQuery: String,
            val movies: Set<Movie>
        ) : SearchAction()

        data class LoadSearchResultsFailed(
            val error: Throwable
        ) : SearchAction()

        object ClearSearch : SearchAction()

    }

    sealed class FavouritesAction : AppAction() {

        object LoadFavourites : FavouritesAction()

        data class LoadFavouritesSucceeded(
            val movies: Set<Movie>
        ) : FavouritesAction()

        data class LoadFavouritesFailed(
            val error: Throwable
        ) : FavouritesAction()

        data class AddToFavourites(val movie: Movie) : FavouritesAction()
        data class AddToFavouritesSucceeded(val movie: Movie) : FavouritesAction()
        object AddToFavouritesFailed : FavouritesAction()

        data class RemoveFromFavourites(val movie: Movie) : FavouritesAction()
        data class RemoveFromFavouritesSucceeded(val movie: Movie) : FavouritesAction()
        object RemoveFromFavouritesFailed : FavouritesAction()

    }
}