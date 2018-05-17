package com.aidanvii.mybingelist.search

import com.aidanvii.mybingelist.core.Navigator
import com.aidanvii.mybingelist.di.AppComponent
import com.aidanvii.mybingelist.search.usecases.LoadSearch

class SearchComponent(appComponent: AppComponent) {

    val searchReducer by lazy { SearchReducer() }

    val searchListViewModel by lazy {
        SearchListViewModel(
            store = appComponent.store,
            rxSchedulers = appComponent.rxSchedulers,
            navigator = navigator
        ).apply { init() }
    }

    val loadSearch by lazy {
        LoadSearch(
            apolloClient = appComponent.networkComponent.apolloClient,
            rxSchedulers = appComponent.rxSchedulers
        )
    }

    val navigator by lazy { Navigator() }
}