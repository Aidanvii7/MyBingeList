package com.aidanvii.mybingelist.search

import com.aidanvii.mybingelist.core.entities.Movie
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.mybingelist.movie
import org.amshove.kluent.`should be null`
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class SearchReducerTest {

    val tested = SearchReducer()
    lateinit var appState: AppState
    lateinit var searchState: AppState.MoviesSection.Search

    @Nested
    inner class `When state is default` {

        init {
            appState = AppState.DEFAULT
            searchState = appState.searchSection
        }

        @Test
        fun `searchQuery is null`() {
            searchState.searchQuery.`should be null`()
        }

        @Test
        fun `LoadingState is NotLoaded with no error`() {
            searchState.loadingState `should equal` LoadingState.NotLoaded(error = null)
        }

        @Nested
        inner class `When given action is LoadSearch` {

            val searchQuery = "adventure"

            init {
                searchState = tested.reduce(
                    state = searchState,
                    action = AppAction.SearchAction.LoadSearch(searchQuery)
                )
            }

            @Test
            fun `movies are empty`() {
                searchState.movies `should be` emptySet<Movie>()
            }

            @Test
            fun `LoadingState is Loading`() {
                searchState.loadingState `should be` LoadingState.Loading
            }

            @Nested
            inner class `When given action is LoadSearchResultsSucceeded` {

                val expectedMovies = setOf(
                    movie(id = 1),
                    movie(id = 2)
                )

                init {
                    searchState = tested.reduce(
                        state = searchState,
                        action = AppAction.SearchAction.LoadSearchResultsSucceeded(
                            searchQuery = searchQuery,
                            movies = expectedMovies
                        )
                    )
                }

                @Test
                fun `movies are correct`() {
                    searchState.movies `should equal` expectedMovies
                }

                @Test
                fun `LoadingState is Loading`() {
                    searchState.loadingState `should be` LoadingState.Loaded
                }

                @Nested
                inner class `When given action is ClearSearch` {

                    init {
                        searchState = tested.reduce(
                            state = searchState,
                            action = AppAction.SearchAction.ClearSearch
                        )
                    }

                    @Test
                    fun `movies are empty`() {
                        searchState.movies `should be` emptySet<Movie>()
                    }

                    @Test
                    fun `LoadingState is NotLoaded with empty error`() {
                        searchState.loadingState `should equal` LoadingState.NotLoaded(null)
                    }
                }
            }

            @Nested
            inner class `When given action is LoadSearchResultsFailed` {

                val expectedError = object : Throwable() {}

                init {
                    searchState = tested.reduce(
                        state = searchState,
                        action = AppAction.SearchAction.LoadSearchResultsFailed(expectedError)
                    )
                }

                @Test
                fun `movies are empty`() {
                    searchState.movies `should be` emptySet<Movie>()
                }

                @Test
                fun `LoadingState is NotLoaded with expected error`() {
                    searchState.loadingState `should equal` LoadingState.NotLoaded(expectedError)
                }

                @Nested
                inner class `When given action is ClearSearch` {

                    init {
                        searchState = tested.reduce(
                            state = searchState,
                            action = AppAction.SearchAction.ClearSearch
                        )
                    }

                    @Test
                    fun `movies are empty`() {
                        searchState.movies `should be` emptySet<Movie>()
                    }

                    @Test
                    fun `LoadingState is NotLoaded with empty error`() {
                        searchState.loadingState `should equal` LoadingState.NotLoaded(null)
                    }
                }
            }
        }
    }
}