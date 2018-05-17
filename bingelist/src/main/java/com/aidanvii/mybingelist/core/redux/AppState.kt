package com.aidanvii.mybingelist.core.redux

import com.aidanvii.mybingelist.core.entities.Movie

data class AppState(
    val genres: Genres,
    val searchSection: MoviesSection.Search,
    val favouritesSection: MoviesSection.Favourites
) {

    sealed class MoviesSection {
        abstract val movies: Set<Movie>
        abstract val loadingState: LoadingState

        data class Search(
            val searchQuery: String?,
            override val movies: Set<Movie>,
            override val loadingState: LoadingState
        ) : MoviesSection()

        data class Favourites(
            override val movies: Set<Movie>,
            override val loadingState: LoadingState
        ) : MoviesSection()
    }

    data class Genres(
        val genres: Set<String>,
        val loadingState: LoadingState
    )

    companion object {

        val DEFAULT = AppState(
            genres = Genres(
                genres = emptySet(),
                loadingState = LoadingState.NotLoaded(
                    error = null
                )
            ),
            searchSection = MoviesSection.Search(
                searchQuery = null,
                movies = emptySet(),
                loadingState = LoadingState.NotLoaded(
                    error = null
                )
            ),
            favouritesSection = MoviesSection.Favourites(
                movies = emptySet(),
                loadingState = LoadingState.NotLoaded(
                    error = null
                )
            )
        )
    }
}