package com.aidanvii.mybingelist.favourites.usecases

import com.aidanvii.mybingelist.core.FavouritesQuery
import com.aidanvii.mybingelist.core.entities.Movie
import com.aidanvii.mybingelist.core.mappers.toMovie
import com.aidanvii.toolbox.rxutils.RxSchedulers
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Single

class LoadFavourites(
    private val apolloClient: ApolloClient,
    private val rxSchedulers: RxSchedulers
) : () -> Single<Set<Movie>> {

    override fun invoke(): Single<Set<Movie>> =
        Single.fromObservable(
            Rx2Apollo.from(
                apolloClient.query(FavouritesQuery.builder().build())
            ).subscribeOn(rxSchedulers.io).map {
                it.data()?.favorites()?.map { it.toMovie() }?.toSet() ?: emptySet()
            }
        )
}

