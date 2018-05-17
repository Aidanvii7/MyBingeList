package com.aidanvii.mybingelist.genres

import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.core.redux.LoadingState
import org.amshove.kluent.`should be`
import org.amshove.kluent.`should equal`
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test

class GenresReducerTest {

    val tested = GenresReducer()
    lateinit var appState: AppState
    lateinit var genresState: AppState.Genres

    @Nested
    inner class `When state is default` {

        init {
            appState = AppState.DEFAULT
            genresState = appState.genres
        }

        @Test
        fun `genres are empty`() {
            genresState.genres `should be` emptySet<String>()
        }

        @Test
        fun `LoadingState is NotLoaded with no error`() {
            genresState.loadingState `should equal` LoadingState.NotLoaded(error = null)
        }

        @Nested
        inner class `When given action is LoadGenres` {

            init {
                genresState = tested.reduce(
                    state = genresState,
                    action = AppAction.GenresAction.LoadGenres
                )
            }

            @Test
            fun `genres are empty`() {
                genresState.genres `should be` emptySet<String>()
            }

            @Test
            fun `LoadingState is Loading`() {
                genresState.loadingState `should be` LoadingState.Loading
            }

            @Nested
            inner class `When given action is LoadGenresSucceeded` {

                val expectedGenres = setOf("action", "adventure", "thriller", "horror")

                init {
                    genresState = tested.reduce(
                        state = genresState,
                        action = AppAction.GenresAction.LoadGenresSucceeded(expectedGenres)
                    )
                }

                @Test
                fun `genres are correct`() {
                    genresState.genres `should equal` expectedGenres
                }

                @Test
                fun `LoadingState is Loading`() {
                    genresState.loadingState `should be` LoadingState.Loaded
                }
            }

            @Nested
            inner class `When given action is LoadGenresFailed` {

                val expectedError = object : Throwable() {}

                init {
                    genresState = tested.reduce(
                        state = genresState,
                        action = AppAction.GenresAction.LoadGenresFailed(expectedError)
                    )
                }

                @Test
                fun `genres are empty`() {
                    genresState.genres `should be` emptySet<String>()
                }

                @Test
                fun `LoadingState is NotLoaded with expected error`() {
                    genresState.loadingState `should equal` LoadingState.NotLoaded(expectedError)
                }
            }
        }
    }
}