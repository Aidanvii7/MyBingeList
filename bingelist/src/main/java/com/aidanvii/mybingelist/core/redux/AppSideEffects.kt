package com.aidanvii.mybingelist.core.redux

import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.AddToFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.AddToFavouritesFailed
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.AddToFavouritesSucceeded
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavouritesFailed
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.LoadFavouritesSucceeded
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.RemoveFromFavourites
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.RemoveFromFavouritesFailed
import com.aidanvii.mybingelist.core.redux.AppAction.FavouritesAction.RemoveFromFavouritesSucceeded
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenres
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenresFailed
import com.aidanvii.mybingelist.core.redux.AppAction.GenresAction.LoadGenresSucceeded
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearch
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearchResultsFailed
import com.aidanvii.mybingelist.core.redux.AppAction.SearchAction.LoadSearchResultsSucceeded
import com.aidanvii.toolbox.redux.Store
import com.aidanvii.toolbox.rxutils.RxSchedulers
import com.aidanvii.toolbox.rxutils.disposable
import io.reactivex.disposables.Disposable
import io.reactivex.rxkotlin.subscribeBy
import java.util.concurrent.atomic.AtomicBoolean
import com.aidanvii.mybingelist.favourites.usecases.AddToFavourites as AddToFavouritesUseCase
import com.aidanvii.mybingelist.favourites.usecases.LoadFavourites as LoadFavouritesUseCase
import com.aidanvii.mybingelist.favourites.usecases.RemoveFromFavourites as RemoveFromFavouritesUseCase
import com.aidanvii.mybingelist.genres.usecases.LoadGenres as LoadGenresUseCase
import com.aidanvii.mybingelist.search.usecases.LoadSearch as LoadSearchUseCase


class AppSideEffects(
    private val store: Store<AppAction, AppState>,
    private val rxSchedulers: RxSchedulers,
    private val loadGenres: LoadGenresUseCase,
    private val loadSearch: LoadSearchUseCase,
    private val loadFavourites: LoadFavouritesUseCase,
    private val addToFavourites: AddToFavouritesUseCase,
    private val removeFromFavourites: RemoveFromFavouritesUseCase
) {

    private val initialised = AtomicBoolean(false)

    private var storeDisposable by disposable()
    private var loadGenresDisposable by disposable()
    private var allMoviesByGenreDisposable by disposable()
    private var loadFavouritesDisposable by disposable()
    private var addToFavouritesDisposable by disposable()
    private var removeFromFavouritesDisposable by disposable()

    fun init() {
        if (!initialised.getAndSet(true)) {
            storeDisposable = subscribeToStore()
            store.dispatch(LoadGenres)
            store.dispatch(LoadFavourites)
        }
    }

    private fun subscribeToStore(): Disposable {
        return store.actionStateObservable
            .subscribeOn(rxSchedulers.single)
            .subscribeBy { handle(it) }
    }

    private fun handle(actionStatePair: Store.ActionStatePair<AppAction, AppState>) {
        actionStatePair.action.apply {
            when (this) {
                is LoadGenres -> handleLoadGenres()
                is LoadSearch -> handleLoadSearch(this)
                is LoadFavourites -> handleLoadFavourites()
                is AddToFavourites -> handleAddToFavourites(this)
                is RemoveFromFavourites -> handleRemoveFromFavourites(this)
            }
        }
    }

    private fun handleLoadGenres() {
        loadGenresDisposable = loadGenres()
            .observeOn(rxSchedulers.single)
            .subscribeBy(
                onError = { store.dispatch(LoadGenresFailed(it)) },
                onSuccess = { genres ->
                    store.dispatch(
                        action = LoadGenresSucceeded(
                            genres = genres
                        )
                    )
                }
            )
    }

    private fun handleLoadSearch(action: LoadSearch) {
        allMoviesByGenreDisposable = loadSearch(action.searchQuery)
            .observeOn(rxSchedulers.single)
            .subscribeBy(
                onError = { store.dispatch(LoadSearchResultsFailed(it)) },
                onSuccess = { movies ->
                    store.dispatch(
                        action = LoadSearchResultsSucceeded(
                            searchQuery = action.searchQuery,
                            movies = movies
                        )
                    )
                }
            )
    }

    private fun handleLoadFavourites() {
        loadFavouritesDisposable = loadFavourites()
            .observeOn(rxSchedulers.single)
            .subscribeBy(
                onError = { store.dispatch(LoadFavouritesFailed(it)) },
                onSuccess = { movies ->
                    store.dispatch(
                        action = LoadFavouritesSucceeded(
                            movies = movies
                        )
                    )
                }
            )
    }

    private fun handleAddToFavourites(action: AddToFavourites) {
        addToFavouritesDisposable = addToFavourites(action.movie.id)
            .observeOn(rxSchedulers.single)
            .subscribeBy(
                onError = { store.dispatch(AddToFavouritesFailed) },
                onComplete = { store.dispatch(AddToFavouritesSucceeded(action.movie)) }
            )
    }

    private fun handleRemoveFromFavourites(action: RemoveFromFavourites) {
        removeFromFavouritesDisposable = removeFromFavourites(action.movie.id)
            .observeOn(rxSchedulers.single)
            .subscribeBy(
                onError = { store.dispatch(RemoveFromFavouritesFailed) },
                onComplete = { store.dispatch(RemoveFromFavouritesSucceeded(action.movie)) }
            )
    }

    fun dispose() {
        storeDisposable = null
        loadGenresDisposable = null
        allMoviesByGenreDisposable = null
        loadFavouritesDisposable = null
        addToFavouritesDisposable = null
        removeFromFavouritesDisposable = null
    }
}