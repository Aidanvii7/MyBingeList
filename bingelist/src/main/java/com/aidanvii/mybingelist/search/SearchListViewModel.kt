package com.aidanvii.mybingelist.search

import android.databinding.Bindable
import com.aidanvii.mybingelist.R
import com.aidanvii.mybingelist.core.MovieAdapterItem
import com.aidanvii.mybingelist.core.MovieListViewModel
import com.aidanvii.mybingelist.core.Navigator
import com.aidanvii.mybingelist.core.logger.logD
import com.aidanvii.mybingelist.core.redux.AppAction
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenres
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.ClearSearch
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearch
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.core.redux.LoadingState
import com.aidanvii.mybingelist.core.redux.LoadingState.Loaded
import com.aidanvii.mybingelist.core.redux.LoadingState.Loading
import com.aidanvii.mybingelist.core.redux.LoadingState.NotLoaded
import com.aidanvii.mybingelist.favourites.FavouritesActivity
import com.aidanvii.toolbox.databinding.bindable
import com.aidanvii.toolbox.delegates.observable.rxjava.doOnNext
import com.aidanvii.toolbox.delegates.observable.rxjava.filter
import com.aidanvii.toolbox.delegates.observable.rxjava.throttleWithTimeOut
import com.aidanvii.toolbox.delegates.observable.rxjava.toRx
import com.aidanvii.toolbox.redux.Store
import com.aidanvii.toolbox.rxutils.RxSchedulers
import java.util.concurrent.TimeUnit

class SearchListViewModel(
    store: Store<AppAction, AppState>,
    rxSchedulers: RxSchedulers,
    private val navigator: Navigator
) : MovieListViewModel(store, rxSchedulers) {

    @get:Bindable("lastKnownState")
    val hint: Int
        get() = lastKnownState?.searchSection?.run {
            when (loadingState) {
                is Loading -> R.string.hint_empty
                is Loaded -> when {
                    searchQuery.isNullOrBlank() -> R.string.hint_prompt
                    movies.isEmpty() -> R.string.hint_no_results
                    else -> R.string.hint_empty
                }
                is NotLoaded -> loadingState.error?.let { R.string.hint_error } ?: R.string.hint_prompt
            }
        } ?: R.string.hint_prompt

    @get:Bindable("lastKnownState")
    val showLoader: Boolean
        get() = lastKnownState?.run { searchSection.loadingState == LoadingState.Loading } ?: false

    @get:Bindable("lastKnownState")
    val genres: Set<String>
        get() = lastKnownState?.run { genres.genres } ?: emptySet()

    @get:Bindable
    var searchQuery: CharSequence by bindable<CharSequence>("")
        .toRx()
        .throttleWithTimeOut(
            timeout = 1000L,
            timeUnit = TimeUnit.MILLISECONDS,
            scheduler = rxSchedulers.computation
        ).filter { it != lastKnownState?.searchSection?.searchQuery.toString() }
        .doOnNext { newQuery ->
            dispatchSearchQuery(newQuery)
        }

    fun refresh() {
        dispatchSearchQuery(searchQuery)
        store.dispatch(LoadGenres)
        store.dispatch(LoadFavourites)
    }

    fun clearSearchQuery() {
        store.dispatch(ClearSearch)
    }

    fun goToFavourites() {
        navigator.navigateTo(FavouritesActivity::class.java)
    }

    override fun onNextState(state: AppState) {
        if (searchQuery == "") {
            searchQuery = state.searchSection.searchQuery ?: ""
        }
    }

    override fun toMovieAdapterItems(state: AppState): List<MovieAdapterItem> {
        return state.searchSection.movies.map { movie -> MovieAdapterItem(movie, store) }
    }

    private fun dispatchSearchQuery(newQuery: CharSequence) {
        logD("search searchQuery: $newQuery")
        store.dispatch(LoadSearch(newQuery.toString()))
    }
}