package com.aidanvii.mybingelist.search

import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.ClearSearch
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearch
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearchResultsFailed
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearchResultsSucceeded
import com.aidanvii.mybingelist.core.redux.AppState.MoviesSection.Search
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.toolbox.redux.Store

class SearchReducer : Store.Reducer<SearchAction, Search> {

    override fun reduce(
        state: Search,
        action: SearchAction
    ) = when (action) {
        is LoadSearch -> handleLoadSearch(state, action)
        is LoadSearchResultsSucceeded -> handleLoadSearchResultsSucceeded(state, action)
        is LoadSearchResultsFailed -> handleLoadSearchResultsFailed(state, action)
        is ClearSearch -> handleClearSearch(state)
    }

    private fun handleLoadSearch(
        searchState: Search,
        loadSearchAction: LoadSearch
    ) = searchState.copy(
        searchQuery = loadSearchAction.searchQuery,
        movies = emptySet(),
        loadingState = LoadingState.Loading
    )

    private fun handleClearSearch(searchState: Search) = searchState.copy(
        movies = emptySet(),
        loadingState = LoadingState.NotLoaded(
            error = null
        )
    )

    private fun handleLoadSearchResultsSucceeded(
        searchState: Search,
        loadSearchResultsSucceeded: LoadSearchResultsSucceeded
    ) = searchState.copy(
        movies = loadSearchResultsSucceeded.movies,
        loadingState = LoadingState.Loaded
    )

    private fun handleLoadSearchResultsFailed(
        searchState: Search,
        loadSearchResultsFailed: LoadSearchResultsFailed
    ) = searchState.copy(
        movies = emptySet(),
        loadingState = LoadingState.NotLoaded(
            error = loadSearchResultsFailed.error
        )
    )
}