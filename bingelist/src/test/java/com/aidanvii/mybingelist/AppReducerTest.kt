package com.aidanvii.mybingelist

import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.mybingelist.favourites.FavouritesReducer
import com.aidanvii.mybingelist.genres.GenresReducer
import com.aidanvii.mybingelist.search.SearchReducer
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class AppReducerTest {

    val genresReducer = GenresReducer()
    val searchReducer = SearchReducer()
    val favouritesReducer = FavouritesReducer()

    val tested = AppReducer(
        genresReducer = genresReducer,
        searchReducer = searchReducer,
        favouritesReducer = favouritesReducer
    )
    var appState: AppState = AppState.DEFAULT

    @Nested
    inner class `When Search results exist and Favourites action is processed` {

        val searchedMovies = setOf(
            movie(id = 1),
            movie(id = 2),
            movie(id = 3),
            movie(id = 4)
        )

        val favouriteMovies = setOf(
            movie(id = 1, isFavourite = true),
            movie(id = 3, isFavourite = true)
        )

        init {
            appState = tested.reduce(
                state = appState.copy(
                    searchSection = appState.searchSection.copy(
                        movies = searchedMovies,
                        loadingState = LoadingState.Loaded
                    )
                ),
                action = AppAction.FavouritesAction.LoadFavouritesSucceeded(
                    movies = favouriteMovies
                )
            )
        }

        @Nested
        inner class `When Favourites action is processed` {
            @Test
            fun `search results are updated from favourites`() {
                appState.searchSection.movies.containsAll(favouriteMovies)
            }
        }
    }
}