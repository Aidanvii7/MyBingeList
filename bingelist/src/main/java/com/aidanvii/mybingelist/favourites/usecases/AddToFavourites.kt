package com.aidanvii.mybingelist.favourites.usecases

import com.aidanvii.mybingelist.core.AddFavouriteMutation
import com.aidanvii.toolbox.rxutils.RxSchedulers
import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import io.reactivex.Completable

class AddToFavourites(
    private val apolloClient: ApolloClient,
    private val rxSchedulers: RxSchedulers
) : (Long) -> Completable {

    override fun invoke(movieId: Long): Completable =
        Completable.fromObservable(
            Rx2Apollo.from(
                apolloClient.mutate(
                    AddFavouriteMutation.builder()
                        .movieId(movieId)
                        .build()
                )
            ).subscribeOn(rxSchedulers.io)
        )
}