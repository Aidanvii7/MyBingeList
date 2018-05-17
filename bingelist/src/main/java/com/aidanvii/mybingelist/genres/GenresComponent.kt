package com.aidanvii.mybingelist.genres

import com.aidanvii.mybingelist.di.AppComponent
import com.aidanvii.mybingelist.genres.usecases.LoadGenres

class GenresComponent(appComponent: AppComponent) {

    val genresReducer by lazy { GenresReducer() }

    val loadGenres by lazy {
        LoadGenres(
            apolloClient = appComponent.networkComponent.apolloClient,
            rxSchedulers = appComponent.rxSchedulers
        )
    }
}