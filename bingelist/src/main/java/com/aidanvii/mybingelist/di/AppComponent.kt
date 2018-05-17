package com.aidanvii.mybingelist.di

import android.content.Context
import com.aidanvii.mybingelist.App
import com.aidanvii.mybingelist.AppReducer
import com.aidanvii.mybingelist.core.redux.AppSideEffects
import com.aidanvii.mybingelist.core.redux.AppState
import com.aidanvii.mybingelist.favourites.FavouritesComponent
import com.aidanvii.mybingelist.genres.GenresComponent
import com.aidanvii.mybingelist.search.SearchComponent
import com.aidanvii.toolbox.redux.Store
import com.aidanvii.toolbox.rxutils.RxSchedulers

class AppComponent(val app: App) {

    val applicationContext: Context get() = app

    val rxSchedulers by lazy {
        RxSchedulers.DefaultSchedulers()
    }

    val dataBindingComponent by lazy {
        DataBindingComponent()
    }

    val networkComponent by lazy {
        NetworkComponent(this)
    }

    val genresComponent by lazy {
        GenresComponent(this)
    }

    val searchComponent by lazy {
        SearchComponent(this)
    }

    val favouritesComponent by lazy {
        FavouritesComponent(this)
    }

    val store by lazy {
        Store(
            rxSchedulers = rxSchedulers,
            reducer = AppReducer(
                genresReducer = genresComponent.genresReducer,
                searchReducer = searchComponent.searchReducer,
                favouritesReducer = favouritesComponent.favouritesReducer
            ),
            state = AppState.DEFAULT
        )
    }

    val appSideEffects by lazy {
        AppSideEffects(
            store = store,
            rxSchedulers = rxSchedulers,
            loadGenres = genresComponent.loadGenres,
            loadSearch = searchComponent.loadSearch,
            loadFavourites = favouritesComponent.loadFavourites,
            addToFavourites = favouritesComponent.addToFavourites,
            removeFromFavourites = favouritesComponent.removeFromFavourites
        )
    }
}