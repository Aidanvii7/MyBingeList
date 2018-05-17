package com.aidanvii.mybingelist.genres

import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenres
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenresFailed
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenresSucceeded
import com.aidanvii.mybingelist.core.redux.AppState.Genres
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.toolbox.redux.Store

class GenresReducer : Store.Reducer<GenresAction, Genres> {

    override fun reduce(
        state: Genres,
        action: GenresAction
    ) = when (action) {
        is LoadGenres -> handleLoadGenres(state)
        is LoadGenresSucceeded -> handleLoadGenresSucceeded(action, state)
        is LoadGenresFailed -> handleLoadGenresFailed(action, state)
    }

    private fun handleLoadGenres(genresState: Genres) = genresState.copy(
        genres = emptySet(),
        loadingState = LoadingState.Loading
    )

    private fun handleLoadGenresSucceeded(
        loadGenresResultsSucceeded: LoadGenresSucceeded,
        genresState: Genres
    ) = genresState.copy(
        genres = loadGenresResultsSucceeded.genres,
        loadingState = LoadingState.Loaded
    )

    private fun handleLoadGenresFailed(
        loadGenresResultsFailed: LoadGenresFailed,
        genresState: Genres
    ) = genresState.copy(
        genres = emptySet(),
        loadingState = LoadingState.NotLoaded(
            error = loadGenresResultsFailed.error
        )
    )
}