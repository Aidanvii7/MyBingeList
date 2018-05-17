package com.aidanvii.mybingelist.favourites

import com.aidanvii.mybingelist.core.entities.Movie
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.mybingelist.movie
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class FavouritesReducerTest {

    val tested = FavouritesReducer()
    lateinit var appState: AppState
    lateinit var favouritesState: AppState.MoviesSection.Favourites

    @Nested
    inner class `When state is default` {

        init {
            appState = AppState.DEFAULT
            favouritesState = appState.favouritesSection
        }

        @Test
        fun `LoadingState is NotLoaded with no error`() {
            favouritesState.loadingState `should equal` LoadingState.NotLoaded(error = null)
        }

        @Nested
        inner class `When given action is LoadFavourites` {

            val searchQuery = "adventure"

            init {
                favouritesState = tested.reduce(
                    state = favouritesState,
                    action = AppAction.FavouritesAction.LoadFavourites
                )
            }

            @Test
            fun `favourites are empty`() {
                favouritesState.movies `should be` emptySet<Movie>()
            }

            @Test
            fun `LoadingState is Loading`() {
                favouritesState.loadingState `should be` LoadingState.Loading
            }

            @Nested
            inner class `When given action is LoadFavouritesSucceeded` {

                val givenMovies = setOf(
                    movie(id = 1),
                    movie(id = 2)
                )
                val expectedMovies = setOf(
                    movie(id = 1, isFavourite = true),
                    movie(id = 2, isFavourite = true)
                )

                init {
                    favouritesState = tested.reduce(
                        state = favouritesState,
                        action = AppAction.FavouritesAction.LoadFavouritesSucceeded(
                            movies = givenMovies
                        )
                    )
                }

                @Test
                fun `movies are correct`() {
                    favouritesState.movies `should equal` expectedMovies.map { it.copy(isFavourite = true) }.toSet()
                }

                @Test
                fun `LoadingState is Loading`() {
                    favouritesState.loadingState `should be` LoadingState.Loaded
                }

                @Nested
                inner class `When given action is AddToFavourites` {

                    val movie = movie(id = 1)

                    init {
                        favouritesState = tested.reduce(
                            state = favouritesState,
                            action = AppAction.FavouritesAction.AddToFavourites(movie)
                        )
                    }

                    @Test
                    fun `movies are correct`() {
                        favouritesState.movies `should equal` expectedMovies
                    }

                    @Test
                    fun `LoadingState is Loading`() {
                        favouritesState.loadingState `should be` LoadingState.Loading
                    }

                    @Nested
                    inner class `When given action is AddToFavouritesSucceeded` {

                        val movie = movie(id = 1)

                        init {
                            favouritesState = tested.reduce(
                                state = favouritesState,
                                action = AppAction.FavouritesAction.AddToFavouritesSucceeded(movie)
                            )
                        }

                        @Test
                        fun `movies are correct`() {
                            favouritesState.movies `should equal` expectedMovies
                        }

                        @Test
                        fun `LoadingState is Loaded`() {
                            favouritesState.loadingState `should be` LoadingState.Loaded
                        }
                    }

                    @Nested
                    inner class `When given action is AddToFavouritesFailed` {

                        init {
                            favouritesState = tested.reduce(
                                state = favouritesState,
                                action = AppAction.FavouritesAction.AddToFavouritesFailed
                            )
                        }

                        @Test
                        fun `movies are correct`() {
                            favouritesState.movies `should equal` expectedMovies
                        }

                        @Test
                        fun `LoadingState is Loaded`() {
                            favouritesState.loadingState `should be` LoadingState.Loaded
                        }
                    }
                }

                @Nested
                inner class `When given action is RemoveFromFavourites` {

                    val movie = movie(id = 1)

                    init {
                        favouritesState = tested.reduce(
                            state = favouritesState,
                            action = AppAction.FavouritesAction.RemoveFromFavourites(movie)
                        )
                    }

                    @Test
                    fun `movies are correct`() {
                        favouritesState.movies `should equal` expectedMovies
                    }

                    @Test
                    fun `LoadingState is Loading`() {
                        favouritesState.loadingState `should be` LoadingState.Loading
                    }

                    @Nested
                    inner class `When given action is RemoveFromFavouritesSucceeded` {

                        val movie = movie(id = 1)

                        init {
                            favouritesState = tested.reduce(
                                state = favouritesState,
                                action = AppAction.FavouritesAction.RemoveFromFavouritesSucceeded(movie)
                            )
                        }

                        @Test
                        fun `movies are correct`() {
                            favouritesState.movies `should equal` expectedMovies.minus(movie.copy(isFavourite = true))
                        }

                        @Test
                        fun `LoadingState is Loaded`() {
                            favouritesState.loadingState `should be` LoadingState.Loaded
                        }
                    }

                    @Nested
                    inner class `When given action is RemoveFromFavouritesFailed` {

                        init {
                            favouritesState = tested.reduce(
                                state = favouritesState,
                                action = AppAction.FavouritesAction.AddToFavouritesFailed
                            )
                        }

                        @Test
                        fun `movies are correct`() {
                            favouritesState.movies `should equal` expectedMovies
                        }

                        @Test
                        fun `LoadingState is Loaded`() {
                            favouritesState.loadingState `should be` LoadingState.Loaded
                        }
                    }
                }
            }

            @Nested
            inner class `When given action is LoadFavouritesFailed` {

                val expectedError = object : Throwable() {}

                init {
                    favouritesState = tested.reduce(
                        state = favouritesState,
                        action = AppAction.FavouritesAction.LoadFavouritesFailed(expectedError)
                    )
                }

                @Test
                fun `movies are empty`() {
                    favouritesState.movies `should be` emptySet<Movie>()
                }

                @Test
                fun `LoadingState is NotLoaded with expected error`() {
                    favouritesState.loadingState `should equal` LoadingState.NotLoaded(expectedError)
                }
            }
        }
    }
}