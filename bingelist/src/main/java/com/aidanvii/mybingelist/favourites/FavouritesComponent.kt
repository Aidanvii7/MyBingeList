package com.aidanvii.mybingelist.favourites

import com.aidanvii.mybingelist.di.AppComponent
import com.aidanvii.mybingelist.favourites.usecases.AddToFavourites
import com.aidanvii.mybingelist.favourites.usecases.LoadFavourites
import com.aidanvii.mybingelist.favourites.usecases.RemoveFromFavourites

class FavouritesComponent(appComponent: AppComponent) {

    val favouritesReducer by lazy { FavouritesReducer() }

    val favouritesListViewModel by lazy {
        FavouritesListViewModel(
            store = appComponent.store,
            rxSchedulers = appComponent.rxSchedulers
        ).apply { init() }
    }

    val loadFavourites by lazy {
        LoadFavourites(
            apolloClient = appComponent.networkComponent.apolloClient,
            rxSchedulers = appComponent.rxSchedulers
        )
    }

    val addToFavourites by lazy {
        AddToFavourites(
            apolloClient = appComponent.networkComponent.apolloClient,
            rxSchedulers = appComponent.rxSchedulers
        )
    }

    val removeFromFavourites by lazy {
        RemoveFromFavourites(
            apolloClient = appComponent.networkComponent.apolloClient,
            rxSchedulers = appComponent.rxSchedulers
        )
    }
}